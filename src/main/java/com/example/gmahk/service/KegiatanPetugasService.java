package com.example.gmahk.service;

import com.example.gmahk.dto.request.KegiatanPetugasRequest;
import com.example.gmahk.dto.response.KegiatanPetugasResponse;

import java.util.List;

public interface KegiatanPetugasService {
    List<KegiatanPetugasResponse> findByBagian(Integer bagianId);
    KegiatanPetugasResponse save(KegiatanPetugasRequest request);
    KegiatanPetugasResponse update(Integer id, KegiatanPetugasRequest request);
    void delete(Integer id);
}
