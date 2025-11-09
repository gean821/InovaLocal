package com.example.demo.Infrastructure.Interfaces.Repositorio.Interfaces;

import com.example.demo.Domain.Entities.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  BeneficiaryRepository extends JpaRepository<Beneficiary, String> {
}
