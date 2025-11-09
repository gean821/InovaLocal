package com.example.demo.Application.Dtos.Services;
import com.example.demo.Application.Dtos.BeneficiaryDto;
import com.example.demo.Application.Dtos.Exceptions.ResourceNotFoundException;
import com.example.demo.Domain.Entities.Beneficiary;
import com.example.demo.Infrastructure.Interfaces.Repositorio.Interfaces.BeneficiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeneficiaryService {

    private final BeneficiaryRepository repository;

    public BeneficiaryService(BeneficiaryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public BeneficiaryDto create(String name, String cpf, String email, String phone) {
        Beneficiary b = new Beneficiary(name, cpf, email, phone);
        b = repository.save(b);
        return toDto(b);
    }

    public BeneficiaryDto getById(String id) {
        Beneficiary b = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found: " + id));
        return toDto(b);
    }

    public List<BeneficiaryDto> listAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public BeneficiaryDto update(String id, String name, String cpf, String email, String phone) {
        Beneficiary b = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found: " + id));
        b.setName(name); b.setCpfOrDocument(cpf); b.setEmail(email); b.setPhone(phone);
        b = repository.save(b);
        return toDto(b);
    }

    @Transactional
    public void delete(String id) {
        if(!repository.existsById(id)) throw new ResourceNotFoundException("Beneficiary not found: " + id);
        repository.deleteById(id);
    }

    public BeneficiaryDto toDto(Beneficiary b) {
        BeneficiaryDto dto = new BeneficiaryDto();
        dto.setId(b.getId());
        dto.setName(b.getName());
        dto.setCpfOrDocument(b.getCpfOrDocument());
        dto.setEmail(b.getEmail());
        dto.setPhone(b.getPhone());
        return dto;
    }

    public Beneficiary findEntityOrThrow(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found: " + id));
    }
}
