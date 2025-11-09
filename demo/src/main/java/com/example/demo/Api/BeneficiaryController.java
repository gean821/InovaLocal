package com.example.demo.Api;

import com.example.demo.Application.Dtos.BeneficiaryDto;
import com.example.demo.Application.Dtos.Services.BeneficiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {

    private final BeneficiaryService service;

    public BeneficiaryController(BeneficiaryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BeneficiaryDto> create(@RequestBody BeneficiaryDto dto) {
        BeneficiaryDto created = service.create(dto.getName(), dto.getCpfOrDocument(), dto.getEmail(), dto.getPhone());
        return ResponseEntity.created(URI.create("/api/beneficiaries/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiaryDto> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BeneficiaryDto>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeneficiaryDto> update(@PathVariable String id, @RequestBody BeneficiaryDto dto) {
        return ResponseEntity.ok(service.update(id, dto.getName(), dto.getCpfOrDocument(), dto.getEmail(), dto.getPhone()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
