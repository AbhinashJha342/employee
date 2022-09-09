package com.example.employee.exception;

public class DbNotUpdatedException extends RuntimeException{

    private final String message;

    public DbNotUpdatedException(String message) {
        this.message = message;
    }
}
