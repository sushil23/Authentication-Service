package com.sushil.authentication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User extends BaseModel {
    private String fullName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = true)
    private Long phoneNumber;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Session> sessions;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
}
