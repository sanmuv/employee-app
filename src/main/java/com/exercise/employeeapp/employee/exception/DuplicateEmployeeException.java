package com.exercise.employeeapp.employee.exception;

public class DuplicateEmployeeException extends RuntimeException {
    public DuplicateEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
}
