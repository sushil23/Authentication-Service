package com.sushil.authentication.controllers;

import com.sushil.authentication.dtos.CreateRoleRequestDto;
import com.sushil.authentication.dtos.RoleResponseDto;
import com.sushil.authentication.models.Role;
import com.sushil.authentication.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto) {
        Role role = roleService.createRole(createRoleRequestDto.getName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        List<RoleResponseDto> roleResponseDtos = new ArrayList<>();
        for (Role role: roles) {
            roleResponseDtos.add(RoleResponseDto.from(role));
        }

        return new ResponseEntity<>(roleResponseDtos, HttpStatus.OK);
    }
}
