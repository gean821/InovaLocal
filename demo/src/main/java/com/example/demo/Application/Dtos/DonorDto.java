package com.example.demo.Application.Dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DonorDto {
    private String id;
    private String name;
    private String email;
    private String phone;

    public DonorDto() {}

    public DonorDto(String id, String name, String email, String phone) {
        this.id = id; this.name = name; this.email = email; this.phone = phone;
    }

}
