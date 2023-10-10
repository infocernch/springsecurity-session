package com.springsecurity.jwt.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
@Entity
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Users {
    @Id//pk
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk")
    private long id;
    private String email;
    private String password;
    private String role;
    private String username;

    private String provider;
    private String providerId;
    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public Users(long id, String email, String password, String role, String username, String provider, String providerId, Timestamp createDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.username = username;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }
}
