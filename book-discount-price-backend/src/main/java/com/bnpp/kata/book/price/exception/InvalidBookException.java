package com.bnpp.kata.book.price.exception;

import org.springframework.http.HttpStatus;


public class InvalidBookException extends RuntimeException {

    private final HttpStatus status;

    public InvalidBookException(String msg) {
        this(msg, HttpStatus.BAD_REQUEST);
    }

    public InvalidBookException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
