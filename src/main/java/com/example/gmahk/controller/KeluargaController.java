package com.example.gmahk.controller;

import com.example.gmahk.dto.request.KeluargaRequest;
import com.example.gmahk.dto.response.ApiResponse;
import com.example.gmahk.dto.response.KeluargaResponse;
import com.example.gmahk.service.KeluargaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/keluarga")
@RequiredArgsConstructor
public class KeluargaController {

    private final KeluargaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<KeluargaResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Data keluarga", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KeluargaResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Data keluarga", service.findById(id)));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KeluargaResponse>> create(@Valid @RequestBody KeluargaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Keluarga berhasil dibuat", service.save(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KeluargaResponse>> update(@PathVariable Integer id, @Valid @RequestBody KeluargaRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Keluarga berhasil diupdate", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Keluarga berhasil dihapus", null));
    }
}
