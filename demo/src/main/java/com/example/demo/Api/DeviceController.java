package com.example.demo.Api;

import com.example.demo.Application.Dtos.DeviceDto;
import com.example.demo.Application.Dtos.Services.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final com.example.demo.Application.Dtos.Services.DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DeviceDto> create(@RequestBody DeviceDto dto) {
        DeviceDto created = service.create(dto.getType(), dto.getBrand(), dto.getModel(), dto.getCondition(), dto.getDescription());
        return ResponseEntity.created(URI.create("/api/devices/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<DeviceDto>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> update(@PathVariable String id, @RequestBody DeviceDto dto) {
        return ResponseEntity.ok(service.update(id, dto.getType(), dto.getBrand(), dto.getModel(), dto.getCondition(), dto.getDescription()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
