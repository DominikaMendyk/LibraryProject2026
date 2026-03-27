package com.example.library.project.demo.controller;

import com.example.library.project.demo.exception.BookException;
import com.example.library.project.demo.exception.LoginPasswordException;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionController {

    private final Gson gson = new Gson();

    @ExceptionHandler(BookException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 for book issues
    public String handleBookException(BookException e) {
        return toJson(e.getMessage());
    }

    @ExceptionHandler(LoginPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401 for login issues
    public String handleLoginException(LoginPasswordException e) {
        return toJson(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException e) {
        return toJson(e + " Your role does not allow performing this action");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleOtherExceptions(Exception e) {
        return toJson(e.getMessage());
    }

    private String toJson(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("timestamp", new Date());
        return gson.toJson(map);
    }
}