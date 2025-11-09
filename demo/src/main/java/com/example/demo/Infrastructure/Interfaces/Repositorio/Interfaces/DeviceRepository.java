package com.example.demo.Infrastructure.Interfaces.Repositorio.Interfaces;

import com.example.demo.Domain.Entities.Device;
import com.example.demo.Domain.Entities.Enum.DeviceCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, String> {
        List<Device> findByCondition(DeviceCondition condition);
    }
