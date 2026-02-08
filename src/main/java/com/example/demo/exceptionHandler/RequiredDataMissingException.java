package com.example.demo.exceptionHandler;

public class RequiredDataMissingException extends RuntimeException {
    public RequiredDataMissingException(String message) {
        super(message);
    }
}
