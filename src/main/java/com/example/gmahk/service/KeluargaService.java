package com.example.gmahk.service;


import com.example.gmahk.dto.request.KeluargaRequest;
import com.example.gmahk.dto.response.KeluargaResponse;

import java.util.List;

public interface KeluargaService {
    List<KeluargaResponse> findAll();
    KeluargaResponse findById(Integer id);
    KeluargaResponse save(KeluargaRequest request);
    KeluargaResponse update(Integer id, KeluargaRequest request);
    void delete(Integer id);
}
