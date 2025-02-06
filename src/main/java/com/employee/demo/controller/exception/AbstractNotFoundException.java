package com.employee.demo.controller.exception;

import org.springframework.http.HttpStatus;

public abstract class NotFoundExceptionClass extends RuntimeException {
    public HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundExceptionClass(String message) {
        super(message);
    }

    public NotFoundExceptionClass(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundExceptionClass(Throwable cause) {
        super(cause);
    }
}
