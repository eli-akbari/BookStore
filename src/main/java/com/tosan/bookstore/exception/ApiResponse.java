package com.tosan.bookstore.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    private int statusCode;
    private String message;
    private T result;
    private LocalDateTime timestamp;

    public ApiResponse(ResponseStatus status, T result) {
        this.statusCode = status.getCode();
        this.message = status.getMessage();
        this.result = result;
        this.timestamp = LocalDateTime.now();
    }

}
