package com.example.library.project.demo.exception;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

    public static UserException create(String message){
        return new UserException(message);
    }
}
