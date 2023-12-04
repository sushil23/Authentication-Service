package com.sushil.authentication.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String fullName;
    private Long phoneNumber;
    private String email;
    private String password;
}
