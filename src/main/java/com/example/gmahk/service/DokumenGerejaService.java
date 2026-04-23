package com.example.gmahk.service;

import com.example.gmahk.dto.response.DokumenGerejaResponse;
import com.example.gmahk.entity.DokumenGereja;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DokumenGerejaService {
    List<DokumenGerejaResponse> findAll();
    List<DokumenGerejaResponse> findByKategori(String kategori);
    List<DokumenGerejaResponse> search(String judul);
    DokumenGerejaResponse findMetaById(Integer id);
    DokumenGereja findWithDataById(Integer id);   // untuk download — return entity lengkap
    DokumenGerejaResponse upload(MultipartFile file, String judul, String deskripsi,
                                 String kategori, String emailUploader);
    void delete(Integer id);
}
