package com.example.gmahk.service;


import com.example.gmahk.dto.request.AbsensiRequest;
import com.example.gmahk.dto.request.BulkAbsensiRequest;
import com.example.gmahk.dto.response.AbsensiResponse;

import java.util.List;

public interface AbsensiService {
    List<AbsensiResponse> findByKegiatan(Integer kegiatanId);
    List<AbsensiResponse> findByJemaat(Integer jemaatId);
    AbsensiResponse save(AbsensiRequest request);
    List<AbsensiResponse> saveBulk(BulkAbsensiRequest request);
    AbsensiResponse update(Integer id, String statusHadir);
    void delete(Integer id);
    long countHadir(Integer kegiatanId);
}
