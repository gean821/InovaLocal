package com.example.demo.Application.Dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateDonationRequest {

    private String donorId;
    private String beneficiaryId;
    private String deviceId;

    private String notes;

    public CreateDonationRequest() {}

}
