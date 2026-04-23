package com.example.gmahk.controller;

import com.example.gmahk.dto.request.AbsensiRequest;
import com.example.gmahk.dto.request.BulkAbsensiRequest;
import com.example.gmahk.dto.response.AbsensiResponse;
import com.example.gmahk.dto.response.ApiResponse;
import com.example.gmahk.service.AbsensiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/absensi")
@RequiredArgsConstructor
public class AbsensiController {

    private final AbsensiService service;

    @GetMapping("/kegiatan/{kegiatanId}")
    public ResponseEntity<ApiResponse<List<AbsensiResponse>>> findByKegiatan(@PathVariable Integer kegiatanId) {
        return ResponseEntity.ok(ApiResponse.ok("Data absensi kegiatan", service.findByKegiatan(kegiatanId)));
    }

    @GetMapping("/kegiatan/{kegiatanId}/count-hadir")
    public ResponseEntity<ApiResponse<Long>> countHadir(@PathVariable Integer kegiatanId) {
        return ResponseEntity.ok(ApiResponse.ok("Jumlah hadir", service.countHadir(kegiatanId)));
    }

    @GetMapping("/jemaat/{jemaatId}")
    public ResponseEntity<ApiResponse<List<AbsensiResponse>>> findByJemaat(@PathVariable Integer jemaatId) {
        return ResponseEntity.ok(ApiResponse.ok("Data absensi jemaat", service.findByJemaat(jemaatId)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AbsensiResponse>> create(@Valid @RequestBody AbsensiRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Absensi berhasil disimpan", service.save(request)));
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AbsensiResponse>>> createBulk(@Valid @RequestBody BulkAbsensiRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Absensi bulk berhasil disimpan", service.saveBulk(request)));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AbsensiResponse>> updateStatus(
            @PathVariable Integer id, @RequestParam String statusHadir) {
        return ResponseEntity.ok(ApiResponse.ok("Status absensi diupdate", service.update(id, statusHadir)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Absensi berhasil dihapus", null));
    }
}
