package com.sushil.authentication.controllers;

import com.sushil.authentication.dtos.LoginRequestDto;
import com.sushil.authentication.exceptions.IncorrectSessionDetailsException;
import com.sushil.authentication.exceptions.IncorrectUserIdOrPasswordException;
import com.sushil.authentication.exceptions.MaximumLoginLimitException;
import com.sushil.authentication.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) throws IncorrectUserIdOrPasswordException, MaximumLoginLimitException {
        Optional<String> sessionToken = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if (sessionToken.isEmpty()) {
            throw new IncorrectUserIdOrPasswordException("Incorrect email or password");
        }

        return new ResponseEntity<>(sessionToken.get(), HttpStatus.OK);
    }

    @GetMapping ("/validate")
    public ResponseEntity<Boolean> validate(@RequestHeader("Authorization") String authHeader) throws IncorrectSessionDetailsException {
        boolean isValid = authService.validate(authHeader);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
}
