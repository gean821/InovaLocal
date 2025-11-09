package com.example.demo.Domain.Entities;

import com.example.demo.Domain.Entities.Enum.DeviceCondition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "devices")
public class Device {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id = UUID.randomUUID().toString();

    @Setter
    @Column(nullable = false)
    private String type;
    @Setter
    private String brand;
    @Setter
    private String model;

    @Setter
    @Enumerated(EnumType.STRING)
    private DeviceCondition condition;

    @Setter
    private String description;

    private LocalDateTime registeredAt = LocalDateTime.now();

    @Setter
    @OneToOne(mappedBy = "device")
    private Donation donation;

    public Device() {}

    public Device(String type, String brand, String model, DeviceCondition condition, String description) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.condition = condition;
        this.description = description;
    }

}
