package com.example.demo.Domain.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "donations")
@Getter
@Setter
public class Donation {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id = UUID.randomUUID().toString();

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;

    @Setter
    @OneToOne(optional = false)
    @JoinColumn(name = "device_id", unique = true)
    private Device device;

    @Column(nullable = false)
    private LocalDateTime donatedAt = LocalDateTime.now();

    @Setter
    private String notes;

    public Donation() {}

    public Donation(Donor donor, Beneficiary beneficiary, Device device, String notes) {
        this.donor = donor;
        this.beneficiary = beneficiary;
        this.device = device;
        this.notes = notes;
    }

}
