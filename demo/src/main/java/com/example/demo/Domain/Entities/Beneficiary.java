package com.example.demo.Domain.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "beneficiaries")
public class Beneficiary {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String name;

    private String cpfOrDocument;

    private String email;

    private String phone;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Beneficiary() {}

    public Beneficiary(String name, String cpfOrDocument, String email, String phone) {
        this.name = name;
        this.cpfOrDocument = cpfOrDocument;
        this.email = email;
        this.phone = phone;
    }

}
