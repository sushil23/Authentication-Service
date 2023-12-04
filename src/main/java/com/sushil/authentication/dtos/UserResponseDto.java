package com.sushil.authentication.dtos;

import com.sushil.authentication.models.Role;
import com.sushil.authentication.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserResponseDto {
    private String fullName;
    private Long phoneNumber;
    private String email;
    private Set<Role> roles = new HashSet<>();

    public static UserResponseDto from (User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setRoles(user.getRoles());
        userResponseDto.setFullName(user.getFullName());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());

        return userResponseDto;
    }
}
