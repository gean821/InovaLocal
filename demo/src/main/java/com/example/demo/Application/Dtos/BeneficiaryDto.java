package com.example.demo.Application.Dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BeneficiaryDto {
    private String id;
    private String name;
    private String cpfOrDocument;
    private String email;
    private String phone;

    public BeneficiaryDto() {}

}
