package com.tosan.bookstore.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException() {
    }

    public InsufficientStockException(String message) {
        super(message);
    }
}
