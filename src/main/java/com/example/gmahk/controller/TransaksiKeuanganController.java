package com.example.gmahk.controller;


import com.example.gmahk.dto.request.TransaksiKeuanganRequest;
import com.example.gmahk.dto.response.ApiResponse;
import com.example.gmahk.dto.response.KeuanganSummaryResponse;
import com.example.gmahk.dto.response.TransaksiKeuanganResponse;
import com.example.gmahk.service.TransaksiKeuanganService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transaksi-keuangan")
@RequiredArgsConstructor
public class TransaksiKeuanganController {

    private final TransaksiKeuanganService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransaksiKeuanganResponse>>> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String jenis) {
        if (from != null && to != null) return ResponseEntity.ok(ApiResponse.ok("Data transaksi", service.findByTanggal(from, to)));
        if (jenis != null) return ResponseEntity.ok(ApiResponse.ok("Data transaksi", service.findByJenis(jenis)));
        return ResponseEntity.ok(ApiResponse.ok("Data transaksi keuangan", service.findAll()));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<KeuanganSummaryResponse>> getSummary() {
        return ResponseEntity.ok(ApiResponse.ok("Summary keuangan", service.getSummary()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransaksiKeuanganResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Data transaksi", service.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','BENDAHARA')")
    public ResponseEntity<ApiResponse<TransaksiKeuanganResponse>> create(
            @Valid @RequestBody TransaksiKeuanganRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Transaksi berhasil dibuat", service.save(request, userDetails.getUsername())));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BENDAHARA')")
    public ResponseEntity<ApiResponse<TransaksiKeuanganResponse>> update(
            @PathVariable Integer id, @Valid @RequestBody TransaksiKeuanganRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Transaksi berhasil diupdate", service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Transaksi berhasil dihapus", null));
    }
}
