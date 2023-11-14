package com.todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<MyErrorMessage> resourceNotFoundException(NotFoundException ex) {
        MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ValidateException.class})
    public ResponseEntity<MyErrorMessage> wrongData (ValidateException ex) {
        MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ImmutableException.class})
    public ResponseEntity<MyErrorMessage> canNotChangeStatus (ImmutableException ex) {
        MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
