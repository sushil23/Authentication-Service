package com.sushil.authentication.services;

import com.sushil.authentication.models.Role;
import com.sushil.authentication.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    public List<Role> getAllRolesByIds(List<Long> roleIds) {
        return roleRepository.findAllByIdIn(roleIds);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
