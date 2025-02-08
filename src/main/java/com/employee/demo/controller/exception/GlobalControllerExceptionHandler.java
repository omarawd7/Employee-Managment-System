package com.employee.demo.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleNotFoundException(AbstractNotFoundException exc) {
        // return ResponseEntity of the exception.
        log.error("NotFoundException caught by GlobalControllerExceptionHandler, {}", exc.toString());
        return new ResponseEntity<>(exc.getMessage(), exc.status);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception exc) {
        // return ResponseEntity of the exception.
        log.error("Exception caught by GlobalControllerExceptionHandler, {}", exc.toString());
        return new ResponseEntity<>( exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
}