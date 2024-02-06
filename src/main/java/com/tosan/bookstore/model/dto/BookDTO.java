package com.tosan.bookstore.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


@Data
public class BookDTO implements Serializable {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String author;

    private BookGenre genre;

    @Min(value = 0)
    private double price;

    @Min(value = 0)
    private int quantity;

}
