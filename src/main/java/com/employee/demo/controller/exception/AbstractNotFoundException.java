package com.employee.demo.controller.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractNotFoundException extends RuntimeException {
    public HttpStatus status = HttpStatus.NOT_FOUND;

    public AbstractNotFoundException(String message) {
        super(message);
    }

    public AbstractNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractNotFoundException(Throwable cause) {
        super(cause);
    }
}
