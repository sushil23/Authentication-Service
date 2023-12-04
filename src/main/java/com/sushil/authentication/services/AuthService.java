package com.sushil.authentication.services;

import com.sushil.authentication.exceptions.IncorrectSessionDetailsException;
import com.sushil.authentication.exceptions.IncorrectUserIdOrPasswordException;
import com.sushil.authentication.exceptions.MaximumLoginLimitException;
import com.sushil.authentication.exceptions.UserAlreadyExistsException;
import com.sushil.authentication.models.Session;
import com.sushil.authentication.models.User;

import java.util.Optional;

public interface AuthService {
    User signUp(User user) throws UserAlreadyExistsException;
    Session login(String email, String password) throws IncorrectUserIdOrPasswordException, MaximumLoginLimitException;
    boolean validate(String token) throws IncorrectSessionDetailsException;
}
