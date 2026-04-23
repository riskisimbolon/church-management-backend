package com.example.gmahk.service;


import com.example.gmahk.dto.request.KegiatanRequest;
import com.example.gmahk.dto.response.KegiatanResponse;

import java.util.List;

public interface KegiatanService {
    List<KegiatanResponse> findAll();
    KegiatanResponse findById(Integer id);
    KegiatanResponse save(KegiatanRequest request);
    KegiatanResponse update(Integer id, KegiatanRequest request);
    void delete(Integer id);
}
