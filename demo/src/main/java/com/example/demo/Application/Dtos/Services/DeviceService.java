package com.example.demo.Application.Dtos.Services;


import com.example.demo.Application.Dtos.DeviceDto;
import com.example.demo.Application.Dtos.Exceptions.ResourceNotFoundException;
import com.example.demo.Domain.Entities.Device;
import com.example.demo.Domain.Entities.Enum.DeviceCondition;
import com.example.demo.Infrastructure.Interfaces.Repositorio.Interfaces.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public DeviceDto create(String type, String brand, String model, DeviceCondition condition, String description) {
        Device d = new Device(type, brand, model, condition, description);
        d = repository.save(d);
        return toDto(d);
    }

    public DeviceDto getById(String id) {
        Device d = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found: " + id));
        return toDto(d);
    }

    public List<DeviceDto> listAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public DeviceDto update(String id, String type, String brand, String model, DeviceCondition condition, String description) {
        Device d = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found: " + id));
        d.setType(type); d.setBrand(brand); d.setModel(model); d.setCondition(condition); d.setDescription(description);
        d = repository.save(d);
        return toDto(d);
    }

    @Transactional
    public void delete(String id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Device not found: " + id);
        }

        repository.deleteById(id);
    }

    public DeviceDto toDto(Device d) {
        DeviceDto dto = new DeviceDto();
        dto.setId(d.getId());
        dto.setType(d.getType());
        dto.setBrand(d.getBrand());
        dto.setModel(d.getModel());
        dto.setCondition(d.getCondition());
        dto.setDescription(d.getDescription());
        return dto;
    }

    public Device findEntityOrThrow(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found: " + id));
    }
}
