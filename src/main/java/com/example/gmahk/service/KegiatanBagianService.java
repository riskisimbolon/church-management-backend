package com.example.gmahk.service;


import com.example.gmahk.dto.request.KegiatanBagianRequest;
import com.example.gmahk.dto.response.KegiatanBagianResponse;

import java.util.List;

public interface KegiatanBagianService {
    List<KegiatanBagianResponse> findByKegiatan(Integer kegiatanId);
    KegiatanBagianResponse findById(Integer id);
    KegiatanBagianResponse save(KegiatanBagianRequest request);
    KegiatanBagianResponse update(Integer id, KegiatanBagianRequest request);
    void delete(Integer id);
    void reorder(Integer kegiatanId, List<Integer> orderedIds);
}
