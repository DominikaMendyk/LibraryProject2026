package com.example.library.project.demo.exception;

public class BookException extends RuntimeException {
    private BookException(String message){
        super(message);
    }

    public static BookException create(String message){
        return new BookException(message);
    }
}
