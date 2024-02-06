package com.tosan.bookstore.controller;

import com.tosan.bookstore.exception.ApiResponse;
import com.tosan.bookstore.exception.ResponseStatus;
import com.tosan.bookstore.model.dto.BookDTO;
import com.tosan.bookstore.model.dto.BookGenre;
import com.tosan.bookstore.model.entity.BookEntity;
import com.tosan.bookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;


    @GetMapping()
    public ResponseEntity<ApiResponse<List<BookEntity>>> getAllBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<BookEntity> result = bookService.findAllBooksByPagination(page, size).getContent();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,result));
    }

    @GetMapping("/filtered")
    public ResponseEntity<ApiResponse<List<BookEntity>>> getFilteredSortedAndPaginatedBooks(
            @RequestParam("genre") BookGenre genre,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<BookEntity> result = bookService.filterSortAndPaginateBooksByGenre(genre, page, size).getContent();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,result));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookEntity>>> searchBooksByTitle(
            @RequestParam("title") String title,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<BookEntity> result = bookService.searchBooksByTitle(title,page,size).getContent();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,result));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addNewBook(@Valid @RequestBody BookDTO bookDTO) {
        bookService.addNewBook(bookDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,null));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> editBookById(@PathVariable("bookId") Long id, @Valid @RequestBody BookDTO bookDTO) {
        bookService.editBook(id, bookDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,null));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> deleteBookById(@PathVariable("bookId") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,null));
    }
}

