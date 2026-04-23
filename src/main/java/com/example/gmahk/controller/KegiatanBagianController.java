package com.example.gmahk.controller;

import com.example.gmahk.dto.request.KegiatanBagianRequest;
import com.example.gmahk.dto.response.ApiResponse;
import com.example.gmahk.dto.response.KegiatanBagianResponse;
import com.example.gmahk.service.KegiatanBagianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kegiatan-bagian")
@RequiredArgsConstructor
public class KegiatanBagianController {

    private final KegiatanBagianService service;

    @GetMapping("/kegiatan/{kegiatanId}")
    public ResponseEntity<ApiResponse<List<KegiatanBagianResponse>>> findByKegiatan(
            @PathVariable Integer kegiatanId) {
        return ResponseEntity.ok(ApiResponse.ok("Data bagian kegiatan", service.findByKegiatan(kegiatanId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KegiatanBagianResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Data bagian", service.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KegiatanBagianResponse>> create(
            @RequestBody KegiatanBagianRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Bagian berhasil ditambahkan", service.save(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KegiatanBagianResponse>> update(
            @PathVariable Integer id, @RequestBody KegiatanBagianRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Bagian berhasil diupdate", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Bagian berhasil dihapus", null));
    }

    @PatchMapping("/reorder/{kegiatanId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> reorder(
            @PathVariable Integer kegiatanId, @RequestBody List<Integer> orderedIds) {
        service.reorder(kegiatanId, orderedIds);
        return ResponseEntity.ok(ApiResponse.ok("Urutan berhasil diupdate", null));
    }
}
