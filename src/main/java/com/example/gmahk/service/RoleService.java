package com.example.gmahk.service;


import com.example.gmahk.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findById(Integer id);
    Role save(String namaRole);
    void deleteById(Integer id);
}