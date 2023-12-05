package com.todo.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    MessageSource messageSource;

    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<MyErrorMessage> resourceNotFoundException(NotFoundException ex) {
        MyErrorMessage message = new MyErrorMessage(
                LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<MyErrorMessage> userNotFoundException(UserNotFoundException ex) {
        MyErrorMessage message = new MyErrorMessage(
                LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), ex.getLocalizedMessage());

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {CustomValidateException.class})
    public ResponseEntity<MyErrorMessage> wrongData (CustomValidateException ex) {
        MyErrorMessage message = new MyErrorMessage(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ImmutableException.class})
    public ResponseEntity<MyErrorMessage> canNotChangeStatus (ImmutableException ex) {
        MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                ex.getLocalizedMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
//    public ResponseEntity<MyErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        BindingResult result = ex.getBindingResult();
//        List<FieldError> fieldErrors = result.getFieldErrors();
//
//        for (FieldError error : fieldErrors) {
//            if ("password".equals(error.getField())) {
//                String errorMessage = error.getDefaultMessage();
//                MyErrorMessage message = new MyErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errorMessage);
//                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        MyErrorMessage defaultMessage = new MyErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Validation error");
//        return new ResponseEntity<>(defaultMessage, HttpStatus.BAD_REQUEST);
//    }
}
