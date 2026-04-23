package com.example.gmahk.controller;

import com.example.gmahk.dto.request.KegiatanRequest;
import com.example.gmahk.dto.response.ApiResponse;
import com.example.gmahk.dto.response.KegiatanResponse;
import com.example.gmahk.service.KegiatanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/kegiatan")
@RequiredArgsConstructor
public class KegiatanController {

    private final KegiatanService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<KegiatanResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Data kegiatan", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KegiatanResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Data kegiatan", service.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KegiatanResponse>> create(@Valid @RequestBody KegiatanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Kegiatan berhasil dibuat", service.save(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KegiatanResponse>> update(@PathVariable Integer id, @Valid @RequestBody KegiatanRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Kegiatan berhasil diupdate", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Kegiatan berhasil dihapus", null));
    }
}
