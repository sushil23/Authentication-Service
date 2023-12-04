package com.sushil.authentication.controllers;

import com.sushil.authentication.dtos.LoginRequestDto;
import com.sushil.authentication.dtos.SignUpRequestDto;
import com.sushil.authentication.dtos.UserResponseDto;
import com.sushil.authentication.exceptions.IncorrectSessionDetailsException;
import com.sushil.authentication.exceptions.IncorrectUserIdOrPasswordException;
import com.sushil.authentication.exceptions.MaximumLoginLimitException;
import com.sushil.authentication.exceptions.UserAlreadyExistsException;
import com.sushil.authentication.models.Session;
import com.sushil.authentication.models.User;
import com.sushil.authentication.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException {
        User user = new User();

        user.setFullName(signUpRequestDto.getFullName());
        user.setEmail(signUpRequestDto.getEmail());
        user.setPhoneNumber(signUpRequestDto.getPhoneNumber());
        user.setPassword(signUpRequestDto.getPassword());

        User savedUser = authService.signUp(user);

        return new ResponseEntity<>(UserResponseDto.from(savedUser), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws IncorrectUserIdOrPasswordException, MaximumLoginLimitException {
        Session session = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN", session.getToken());
        return new ResponseEntity<>(UserResponseDto.from(session.getUser()), headers, HttpStatus.OK);
    }

    @GetMapping ("/validate")
    public ResponseEntity<Boolean> validate(@RequestHeader("Authorization") String authHeader) throws IncorrectSessionDetailsException {
        boolean isValid = authService.validate(authHeader);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
}
