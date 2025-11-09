package com.example.demo.Application.Dtos.Services;


import com.example.demo.Application.Dtos.CreateDonationRequest;
import com.example.demo.Application.Dtos.Exceptions.ResourceNotFoundException;
import com.example.demo.Domain.Entities.Beneficiary;
import com.example.demo.Domain.Entities.Device;
import com.example.demo.Domain.Entities.Donation;
import com.example.demo.Domain.Entities.Donor;
import com.example.demo.Infrastructure.Interfaces.Repositorio.Interfaces.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonorService donorService;
    private final BeneficiaryService beneficiaryService;
    private final DeviceService deviceService;

    public DonationService(DonationRepository donationRepository,
                           DonorService donorService,
                           BeneficiaryService beneficiaryService,
                           DeviceService deviceService) {
        this.donationRepository = donationRepository;
        this.donorService = donorService;
        this.beneficiaryService = beneficiaryService;
        this.deviceService = deviceService;
    }

    @Transactional
    public Donation create(CreateDonationRequest req) {
        // retrieve entities (throws ResourceNotFoundException if missing)
        Donor donor = donorService.findEntityOrThrow(req.getDonorId());
        Beneficiary beneficiary = beneficiaryService.findEntityOrThrow(req.getBeneficiaryId());
        Device device = deviceService.findEntityOrThrow(req.getDeviceId());

        if (device.getDonation() != null) {
            throw new IllegalStateException("Device already donated: " + device.getId());
        }

        Donation donation = new Donation(donor, beneficiary, device, req.getNotes());
        donation = donationRepository.save(donation);

        // set bidirectional (so device.getDonation() reflects)
        device.setDonation(donation);

        return donation;
    }

    public Donation getById(String id) {
        return donationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Donation not found: " + id));
    }

    public List<Donation> listAll() {
        return donationRepository.findAll();
    }

    public List<Donation> listByDonor(String donorId) {
        return donationRepository.findByDonorId(donorId);
    }

    public List<Donation> listByBeneficiary(String beneficiaryId) {
        return donationRepository.findByBeneficiaryId(beneficiaryId);
    }

    @Transactional
    public void delete(String id) {
        Donation donation = donationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Donation not found: " + id));

        Device device = donation.getDevice();
        if (device != null) {
            device.setDonation(null);
        }
        donationRepository.deleteById(id);
    }
}
