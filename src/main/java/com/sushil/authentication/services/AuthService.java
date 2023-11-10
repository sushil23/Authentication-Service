package com.sushil.authentication.services;

import com.sushil.authentication.exceptions.IncorrectSessionDetailsException;
import com.sushil.authentication.exceptions.IncorrectUserIdOrPasswordException;
import com.sushil.authentication.exceptions.MaximumLoginLimitException;

import java.util.Optional;

public interface AuthService {
    Optional<String> login(String email, String password) throws MaximumLoginLimitException;
    boolean validate(String token) throws IncorrectSessionDetailsException;
}
