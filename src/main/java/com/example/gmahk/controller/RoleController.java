package com.example.gmahk.controller;

import com.example.gmahk.dto.response.ApiResponse;
import com.example.gmahk.entity.Role;
import com.example.gmahk.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Role>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Data roles berhasil diambil", roleService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Role>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Data role berhasil diambil", roleService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Role>> create(@RequestParam String namaRole) {
        Role saved = roleService.save(namaRole);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Role berhasil dibuat", saved));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        roleService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Role berhasil dihapus", null));
    }
}

