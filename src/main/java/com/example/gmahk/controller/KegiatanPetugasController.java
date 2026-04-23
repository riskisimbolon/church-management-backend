package com.example.gmahk.controller;

import com.example.gmahk.dto.request.KegiatanPetugasRequest;
import com.example.gmahk.dto.response.ApiResponse;
import com.example.gmahk.dto.response.KegiatanPetugasResponse;
import com.example.gmahk.service.KegiatanPetugasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kegiatan-petugas")
@RequiredArgsConstructor
public class KegiatanPetugasController {

    private final KegiatanPetugasService service;

    @GetMapping("/bagian/{bagianId}")
    public ResponseEntity<ApiResponse<List<KegiatanPetugasResponse>>> findByBagian(
            @PathVariable Integer bagianId) {
        return ResponseEntity.ok(ApiResponse.ok("Data petugas bagian", service.findByBagian(bagianId)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KegiatanPetugasResponse>> create(
            @RequestBody KegiatanPetugasRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Petugas berhasil ditambahkan", service.save(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KegiatanPetugasResponse>> update(
            @PathVariable Integer id, @RequestBody KegiatanPetugasRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Petugas berhasil diupdate", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Petugas berhasil dihapus", null));
    }
}
