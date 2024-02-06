package com.tosan.bookstore.exception;

public class BookDoesntExistsException extends RuntimeException {

    public BookDoesntExistsException() {
    }

    public BookDoesntExistsException(String message) {
        super(message);
    }
}
