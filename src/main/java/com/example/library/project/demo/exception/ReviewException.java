package com.example.library.project.demo.exception;

public class ReviewException extends RuntimeException {
    public ReviewException(String message) {
        super(message);
    }

    public static ReviewException create(String message){
        return new ReviewException(message);
    }
}
