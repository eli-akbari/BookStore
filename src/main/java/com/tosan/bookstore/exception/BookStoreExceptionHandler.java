package com.tosan.bookstore.exception;

import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookStoreExceptionHandler {

    @ExceptionHandler(BookDoesntExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleBookDoesntExistsException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(ResponseStatus.BOOK_DOESNT_EXISTS_WITH_THIS_ID, null));
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleBookAlreadyExistsException() {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(ResponseStatus.BOOK_ALREADY_EXISTS, null));
    }

    @ExceptionHandler({UnexpectedTypeException.class,MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedTypeException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(ResponseStatus.INVALID_INPUT, null));
    }

    @ExceptionHandler(CustomerDoesntExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomerDoesntExistsException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(ResponseStatus.CUSTOMER_DOESNT_EXISTS, null));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponse<String>> handleInsufficientStockException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(ResponseStatus.INSUFFICIENT_STOCK, null));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleOrderNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(ResponseStatus.ORDER_DOESNT_EXISTS_WITH_THIS_ID, null));
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomerAlreadyException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(ResponseStatus.CUSTOMER_ALREADY_EXISTS, null));
    }
}
