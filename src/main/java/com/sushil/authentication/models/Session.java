package com.sushil.authentication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Sessions")
public class Session extends BaseModel {
    @ManyToOne
    private User user;
    private String token;
}
