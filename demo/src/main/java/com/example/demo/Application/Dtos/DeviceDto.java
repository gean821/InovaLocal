package com.example.demo.Application.Dtos;

import com.example.demo.Domain.Entities.Enum.DeviceCondition;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeviceDto {
    private String id;
    private String type;
    private String brand;
    private String model;
    private DeviceCondition condition;
    private String description;

    public DeviceDto() {}

}
