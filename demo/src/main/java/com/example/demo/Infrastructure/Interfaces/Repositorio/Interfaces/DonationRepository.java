package com.example.demo.Infrastructure.Interfaces.Repositorio.Interfaces;

import com.example.demo.Domain.Entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, String> {
    List<Donation> findByDonorId(String donorId);
    List<Donation> findByBeneficiaryId(String beneficiaryId);
}
