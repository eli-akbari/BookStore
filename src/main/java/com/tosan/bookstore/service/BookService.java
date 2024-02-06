package com.tosan.bookstore.service;

import com.tosan.bookstore.exception.BookAlreadyExistsException;
import com.tosan.bookstore.exception.BookDoesntExistsException;
import com.tosan.bookstore.model.dto.BookDTO;
import com.tosan.bookstore.model.dto.BookGenre;
import com.tosan.bookstore.model.entity.BookEntity;
import com.tosan.bookstore.model.mapper.BookMapper;
import com.tosan.bookstore.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    public Page<BookEntity> findAllBooksByPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        logger.info("Fetching all books by pagination, Page: {}, Size: {}", page, size);
        return bookRepository.findAll(pageable);
    }

    public Page<BookEntity> filterSortAndPaginateBooksByGenre(BookGenre genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("price"));
        logger.info("Filtering, sorting, and paginating books by genre: {}, Page: {}, Size: {}", genre, page, size);
        return bookRepository.findByGenre(genre, pageable);
    }

    public Page<BookEntity> searchBooksByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        logger.info("Searching books by title '{}', Page: {}, Size: {}", title, page, size);
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public void addNewBook(BookDTO bookDTO) {
        if (bookExists(bookDTO)) {
            throw new BookAlreadyExistsException();
        }

        logger.info("Adding new book: {}", bookDTO);
        BookEntity bookEntity = BookMapper.INSTANCE.mapDtoToEntity(bookDTO);
        bookRepository.save(bookEntity);
        logger.info("New book added successfully: {}", bookDTO);
    }

    public void editBook(Long bookId, BookDTO updatedBookDTO) {
        BookEntity existingBook = bookRepository.findById(bookId)
                        .orElseThrow(BookDoesntExistsException::new);
        String newBookTitle = updatedBookDTO.getTitle();
        String newBookAuthor = updatedBookDTO.getAuthor();

        if (newBookTitle.equals(existingBook.getTitle()) && newBookAuthor.equals(existingBook.getAuthor())) {
            throw new BookAlreadyExistsException();
        } else if (bookRepository.existsByTitleAndAuthor(newBookTitle, newBookAuthor)) {
            throw new BookAlreadyExistsException();
        } else {
            logger.info("Editing book with ID {}: {}", bookId, updatedBookDTO);
            BookMapper.INSTANCE.updateBookEntityFromDto(updatedBookDTO,existingBook);
            bookRepository.save(existingBook);
            logger.info("Book with ID {} edited successfully", bookId);
        }
    }

    private boolean bookExists(BookDTO bookDTO) {
        Optional<BookEntity> existingBook = bookRepository.findBookEntityByTitleAndAuthor(
                bookDTO.getTitle(), bookDTO.getAuthor());
        return existingBook.isPresent();
    }

    public void deleteBook(Long bookId) {
        Optional<BookEntity> optionalExistingBook = bookRepository.findById(bookId);

        if (optionalExistingBook.isPresent()) {
            bookRepository.deleteById(bookId);
            logger.info("Book with ID {} deleted successfully", bookId);
        } else {
            throw new BookDoesntExistsException();
        }
    }
}
