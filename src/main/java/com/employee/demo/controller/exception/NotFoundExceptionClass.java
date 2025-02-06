package com.employee.demo.controller.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    public HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
