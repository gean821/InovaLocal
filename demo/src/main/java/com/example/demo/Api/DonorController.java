package com.example.demo.Api;

import com.example.demo.Application.Dtos.DonorDto;
import com.example.demo.Application.Dtos.Services.DonorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService service;

    public DonorController(DonorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DonorDto> create( @RequestBody DonorDto dto) {
        DonorDto created = service.create(dto.getName(), dto.getEmail(), dto.getPhone());
        return ResponseEntity.created(URI.create("/api/donors/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorDto> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<DonorDto>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonorDto> update(@PathVariable String id, @RequestBody DonorDto dto) {
        return ResponseEntity.ok(service.update(id, dto.getName(), dto.getEmail(), dto.getPhone()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
