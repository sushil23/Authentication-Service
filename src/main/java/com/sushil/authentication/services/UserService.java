package com.sushil.authentication.services;

import com.sushil.authentication.exceptions.UserDoesNotExistException;
import com.sushil.authentication.models.Role;
import com.sushil.authentication.models.User;
import com.sushil.authentication.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserDetails(Long userId) {
        return userRepository.findById(userId);
    }

    public User setUserRoles(Long userId, List<Long> roleIds) throws UserDoesNotExistException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserDoesNotExistException("Incorrect user id passed");
        }

        User user = userOptional.get();
        List<Role> rolesList = roleService.getAllRolesByIds(roleIds);
        Set<Role> roles = new HashSet<>(rolesList);
        // roles.addAll(rolesList);

        user.setRoles(roles);
        return userRepository.save(user);
    }
}
