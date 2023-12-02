package com.todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super("Current user not found or not authorized");
    }
}
