package com.example.demo.Domain.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "donors")
public class Donor {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Donor() {}

    public Donor(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
