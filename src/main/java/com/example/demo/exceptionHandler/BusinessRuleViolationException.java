package com.example.demo.exceptionHandler;

public class BusinessRuleViolationException extends  RuntimeException{
    public  BusinessRuleViolationException(String message){
        super(message);
    }
}
