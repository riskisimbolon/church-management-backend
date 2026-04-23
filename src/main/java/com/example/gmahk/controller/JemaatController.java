package com.example.gmahk.controller;


import com.example.gmahk.dto.request.JemaatRequest;
import com.example.gmahk.dto.response.ApiResponse;
import com.example.gmahk.dto.response.JemaatResponse;
import com.example.gmahk.service.JemaatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jemaat")
@RequiredArgsConstructor
public class JemaatController {

    private final JemaatService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<JemaatResponse>>> findAll(
            @RequestParam(required = false) String nama,
            @RequestParam(required = false) String status) {
        if (nama != null) return ResponseEntity.ok(ApiResponse.ok("Hasil pencarian", service.findByNama(nama)));
        if (status != null) return ResponseEntity.ok(ApiResponse.ok("Data jemaat by status", service.findByStatus(status)));
        return ResponseEntity.ok(ApiResponse.ok("Data jemaat", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JemaatResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Data jemaat", service.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<JemaatResponse>> create(@Valid @RequestBody JemaatRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Jemaat berhasil dibuat", service.save(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<JemaatResponse>> update(@PathVariable Integer id, @Valid @RequestBody JemaatRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Jemaat berhasil diupdate", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Jemaat berhasil dihapus", null));
    }

    @GetMapping("/birthday/today")
    public ResponseEntity<?> birthdayToday() {
        return ResponseEntity.ok(service.findBirthdayToday());
    }

    @GetMapping("/birthday/month")
    public ResponseEntity<?> birthdayThisMonth() {
        return ResponseEntity.ok(service.findBirthdayThisMonth());
    }
}
