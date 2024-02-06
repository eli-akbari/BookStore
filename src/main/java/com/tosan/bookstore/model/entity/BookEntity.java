package com.tosan.bookstore.model.entity;

import com.tosan.bookstore.model.dto.BookGenre;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "book")
@Data
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENRE")
    private BookGenre genre;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "QUANTITY")
    private int quantity;
}
