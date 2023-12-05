package com.todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomValidateException extends RuntimeException {
    public CustomValidateException(String message) {
        super(message);
    }
}
