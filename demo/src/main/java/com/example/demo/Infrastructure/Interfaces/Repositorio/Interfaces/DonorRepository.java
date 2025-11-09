package com.example.demo.Infrastructure.Interfaces.Repositorio.Interfaces;

import com.example.demo.Domain.Entities.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, String> {
    Optional<Donor> findByEmail(String email);
}
