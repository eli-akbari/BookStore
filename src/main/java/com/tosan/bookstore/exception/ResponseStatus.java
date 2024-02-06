package com.tosan.bookstore.exception;

import org.springframework.http.HttpStatus;

public enum ResponseStatus {

    SUCCESS(HttpStatus.OK.value(), "Success"),
    BOOK_DOESNT_EXISTS_WITH_THIS_ID(HttpStatus.NOT_FOUND.value(),"Book doesnt exists with this ID"),
    BOOK_ALREADY_EXISTS(HttpStatus.FORBIDDEN.value(), "Book Already Exists"),
    CUSTOMER_ALREADY_EXISTS(HttpStatus.FORBIDDEN.value(), "Customer Already Exists"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST.value(), "Invalid Input"),
    CUSTOMER_DOESNT_EXISTS(HttpStatus.NOT_FOUND.value(), "Customer doesnt exists"),
    INSUFFICIENT_STOCK(HttpStatus.FORBIDDEN.value(), "Not enough stock for book"),
    ORDER_DOESNT_EXISTS_WITH_THIS_ID(HttpStatus.FORBIDDEN.value(), "Order doesn't exists with this ID"),
    ;

    private final int code;
    private final String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
