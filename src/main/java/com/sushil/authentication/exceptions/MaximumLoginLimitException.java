package com.sushil.authentication.exceptions;

public class MaximumLoginLimitException extends Exception {
    public MaximumLoginLimitException(String message) {
        super(message);
    }
}
