package com.sushil.authentication.exceptions;

public class IncorrectUserIdOrPasswordException extends Exception {
    public IncorrectUserIdOrPasswordException(String message) {
        super(message);
    }
}
