package com.example.demo.Application.Dtos.Services;

import com.example.demo.Application.Dtos.DonorDto;
import com.example.demo.Application.Dtos.Exceptions.ResourceNotFoundException;
import com.example.demo.Domain.Entities.Donor;
import com.example.demo.Infrastructure.Interfaces.Repositorio.Interfaces.DonorRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @Transactional
    public DonorDto create(String name, String email, String phone) {
        Donor donor = new Donor(name, email, phone);
        donor = donorRepository.save(donor);
        return toDto(donor);
    }

    public DonorDto getById(String id) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found: " + id));
        return toDto(donor);
    }

    public List<DonorDto> listAll() {
        return donorRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public DonorDto update(String id, String name, String email, String phone) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found: " + id));
        donor.setName(name);
        donor.setEmail(email);
        donor.setPhone(phone);
        donor = donorRepository.save(donor);
        return toDto(donor);
    }

    @Transactional
    public void delete(String id) {
        if (!donorRepository.existsById(id))  {
            throw new ResourceNotFoundException("Donor not found: " + id);
        }

        donorRepository.deleteById(id);
    }

    public DonorDto toDto(Donor donor) {
        return new DonorDto(donor.getId(), donor.getName(), donor.getEmail(), donor.getPhone());
    }

    public Donor findEntityOrThrow(String id) {
        return donorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Donor not found: " + id));
    }
}
