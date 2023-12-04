package com.sushil.authentication.dtos;

import com.sushil.authentication.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDto {
    private Long id;
    private String name;

    public static RoleResponseDto from(Role role) {
        RoleResponseDto roleResponseDto = new RoleResponseDto();

        roleResponseDto.setId(role.getId());
        roleResponseDto.setName(role.getName());

        return roleResponseDto;
    }
}
