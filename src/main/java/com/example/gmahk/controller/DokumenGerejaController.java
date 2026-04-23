package com.example.gmahk.controller;

import com.example.gmahk.dto.response.ApiResponse;
import com.example.gmahk.dto.response.DokumenGerejaResponse;
import com.example.gmahk.entity.DokumenGereja;
import com.example.gmahk.service.DokumenGerejaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dokumen")
@RequiredArgsConstructor
public class DokumenGerejaController {

    private final DokumenGerejaService service;

    // GET list semua dokumen (tanpa data BLOB) — semua user login bisa akses
    @GetMapping
    public ResponseEntity<ApiResponse<List<DokumenGerejaResponse>>> findAll(
            @RequestParam(required = false) String kategori,
            @RequestParam(required = false) String search) {

        List<DokumenGerejaResponse> result;
        if (search != null && !search.isBlank()) {
            result = service.search(search);
        } else if (kategori != null && !kategori.isBlank()) {
            result = service.findByKategori(kategori);
        } else {
            result = service.findAll();
        }
        return ResponseEntity.ok(ApiResponse.ok("Data dokumen", result));
    }

    // GET detail meta dokumen (tanpa data BLOB)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DokumenGerejaResponse>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Detail dokumen", service.findMetaById(id)));
    }

    // GET download file — semua user login bisa download
    @GetMapping("/{id}/download")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Integer id) {
        DokumenGereja dokumen = service.findWithDataById(id);

        ByteArrayResource resource = new ByteArrayResource(dokumen.getData());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dokumen.getTipeFile()))
                .contentLength(dokumen.getUkuran())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + dokumen.getNamaFile() + "\"")
                .body(resource);
    }

    // POST upload — hanya ADMIN
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DokumenGerejaResponse>> upload(
            @RequestParam("file")       MultipartFile file,
            @RequestParam("judul")      String judul,
            @RequestParam(value = "deskripsi", required = false) String deskripsi,
            @RequestParam(value = "kategori",  required = false) String kategori,
            @AuthenticationPrincipal UserDetails userDetails) {

        DokumenGerejaResponse response = service.upload(
                file, judul, deskripsi, kategori, userDetails.getUsername());

        return ResponseEntity.status(201)
                .body(ApiResponse.ok("Dokumen berhasil diupload", response));
    }

    // DELETE — hanya ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Dokumen berhasil dihapus", null));
    }
}
