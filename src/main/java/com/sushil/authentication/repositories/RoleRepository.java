package com.sushil.authentication.repositories;

import com.sushil.authentication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role save(Role role);

    List<Role> findAll();

    List<Role> findAllByIdIn(List<Long> roleIds);
}
