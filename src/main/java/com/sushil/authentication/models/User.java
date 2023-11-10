package com.sushil.authentication.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Session> sessions;
}
