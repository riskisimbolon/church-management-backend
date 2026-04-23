package com.example.gmahk.service.impl;



import com.example.gmahk.entity.Role;
import com.example.gmahk.repository.RoleRepository;
import com.example.gmahk.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role tidak ditemukan dengan id: " + id));
    }

    @Override
    public Role save(String namaRole) {
        if (roleRepository.findByNamaRole(namaRole).isPresent()) {
            throw new RuntimeException("Role sudah ada: " + namaRole);
        }
        return roleRepository.save(Role.builder().namaRole(namaRole).build());
    }

    @Override
    public void deleteById(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role tidak ditemukan dengan id: " + id);
        }
        roleRepository.deleteById(id);
    }
}

