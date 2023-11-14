package com.todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImmutableException extends RuntimeException {
    public ImmutableException(String message) {
        super(message);
    }
}
