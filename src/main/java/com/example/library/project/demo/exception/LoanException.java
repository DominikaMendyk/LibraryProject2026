package com.example.library.project.demo.exception;

public class LoanException extends RuntimeException {
    public LoanException(String message) {
        super(message);
    }

    public static LoanException create(String message){
        return new LoanException(message);
    }
}
