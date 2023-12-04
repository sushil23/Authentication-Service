package com.sushil.authentication.controllers;

import com.sushil.authentication.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvises {
    @ExceptionHandler(IncorrectUserIdOrPasswordException.class)
    public ResponseEntity<String> handleIncorrectIdPasswordException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectSessionDetailsException.class)
    public ResponseEntity<String> handleIncorrectSessionDetails(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MaximumLoginLimitException.class)
    public ResponseEntity<String> handleMaximumLoginLimitException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<String> handleUserDoesNotExistException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
}
