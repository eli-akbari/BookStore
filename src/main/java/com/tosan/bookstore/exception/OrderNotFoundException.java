package com.tosan.bookstore.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
