package com.employee.demo.controller.exception;


public class EmployeeNotFoundException extends NotFoundExceptionClass {

    public EmployeeNotFoundException(String message) {
        super(message);
    }
    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public EmployeeNotFoundException(Throwable cause) {
        super(cause);
    }
}
