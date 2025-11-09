package com.example.demo.Api;

import com.example.demo.Application.Dtos.CreateDonationRequest;
import com.example.demo.Application.Dtos.Services.DonationService;
import com.example.demo.Domain.Entities.Donation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final DonationService service;

    public DonationController(DonationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Donation> create( @RequestBody CreateDonationRequest req) {
        Donation created = service.create(req);
        return ResponseEntity.created(URI.create("/api/donations/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Donation>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
