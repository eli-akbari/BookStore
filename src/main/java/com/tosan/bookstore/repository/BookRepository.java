package com.tosan.bookstore.repository;

import com.tosan.bookstore.model.dto.BookGenre;
import com.tosan.bookstore.model.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Page<BookEntity> findByGenre(BookGenre genre, Pageable pageable);
    Page<BookEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Optional<BookEntity> findBookEntityByTitleAndAuthor(String title, String author);
    boolean existsByTitleAndAuthor(String title, String author);

}
