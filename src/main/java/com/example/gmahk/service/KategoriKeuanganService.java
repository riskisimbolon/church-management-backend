package com.example.gmahk.service;


import com.example.gmahk.dto.request.KategoriKeuanganRequest;
import com.example.gmahk.dto.response.KategoriKeuanganResponse;

import java.util.List;

public interface KategoriKeuanganService {
    List<KategoriKeuanganResponse> findAll();
    List<KategoriKeuanganResponse> findByTipe(String tipe);
    KategoriKeuanganResponse findById(Integer id);
    KategoriKeuanganResponse save(KategoriKeuanganRequest request);
    KategoriKeuanganResponse update(Integer id, KategoriKeuanganRequest request);
    void delete(Integer id);
}
