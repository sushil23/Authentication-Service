package com.sushil.authentication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Sessions")
public class Session extends BaseModel {
    @ManyToOne
    private User user;
    private String token;
    private Date expiringAt;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
}
