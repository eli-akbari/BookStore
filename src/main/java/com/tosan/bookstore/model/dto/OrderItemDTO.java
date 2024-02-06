package com.tosan.bookstore.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemDTO implements Serializable {

    @NotNull
    private Long bookId;
    private int quantity;
}
