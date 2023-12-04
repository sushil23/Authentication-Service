package com.sushil.authentication.controllers;

import com.sushil.authentication.dtos.SetUserRolesRequestDto;
import com.sushil.authentication.dtos.UserResponseDto;
import com.sushil.authentication.exceptions.UserDoesNotExistException;
import com.sushil.authentication.models.User;
import com.sushil.authentication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> userResponseList = new ArrayList<>();
        for (User user: users) {
            userResponseList.add(UserResponseDto.from(user));
        }
        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserDetails(@PathVariable("id") Long userId) throws UserDoesNotExistException {
        Optional<User> userOptional = userService.getUserDetails(userId);
        if (userOptional.isEmpty()) {
            throw new UserDoesNotExistException("User with id - " + userId + " does not exist.");
        }

        User user = userOptional.get();
        return new ResponseEntity<>(UserResponseDto.from(user), HttpStatus.OK);
    }

    @PatchMapping("/{id}/roles")
    public ResponseEntity<UserResponseDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto setUserRolesRequestDto) throws UserDoesNotExistException {
        User updatedUser = userService.setUserRoles(userId, setUserRolesRequestDto.getRoleIds());
        return new ResponseEntity<>(UserResponseDto.from(updatedUser), HttpStatus.OK);
    }
}
