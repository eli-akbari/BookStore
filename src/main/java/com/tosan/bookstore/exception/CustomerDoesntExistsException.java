package com.tosan.bookstore.exception;

public class CustomerDoesntExistsException extends RuntimeException {

    public CustomerDoesntExistsException() {
    }

    public CustomerDoesntExistsException(String message) {
        super(message);
    }
}
