package com.example.gmahk.controller;


import com.example.gmahk.dto.request.KategoriKeuanganRequest;
import com.example.gmahk.dto.response.ApiResponse;
import com.example.gmahk.dto.response.KategoriKeuanganResponse;
import com.example.gmahk.service.KategoriKeuanganService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/kategori-keuangan")
@RequiredArgsConstructor
public class KategoriKeuanganController {

    private final KategoriKeuanganService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<KategoriKeuanganResponse>>> findAll(
            @RequestParam(required = false) String tipe) {
        if (tipe != null) return ResponseEntity.ok(ApiResponse.ok("Data kategori by tipe", service.findByTipe(tipe)));
        return ResponseEntity.ok(ApiResponse.ok("Data kategori keuangan", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KategoriKeuanganResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Data kategori", service.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KategoriKeuanganResponse>> create(@Valid @RequestBody KategoriKeuanganRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Kategori berhasil dibuat", service.save(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<KategoriKeuanganResponse>> update(@PathVariable Integer id, @Valid @RequestBody KategoriKeuanganRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Kategori berhasil diupdate", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Kategori berhasil dihapus", null));
    }
}
