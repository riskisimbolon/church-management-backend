package com.example.gmahk.service;


import com.example.gmahk.dto.request.TransaksiKeuanganRequest;
import com.example.gmahk.dto.response.KeuanganSummaryResponse;
import com.example.gmahk.dto.response.TransaksiKeuanganResponse;

import java.time.LocalDate;
import java.util.List;

public interface TransaksiKeuanganService {
    List<TransaksiKeuanganResponse> findAll();
    TransaksiKeuanganResponse findById(Integer id);
    List<TransaksiKeuanganResponse> findByTanggal(LocalDate from, LocalDate to);
    List<TransaksiKeuanganResponse> findByJenis(String jenis);
    KeuanganSummaryResponse getSummary();
    TransaksiKeuanganResponse save(TransaksiKeuanganRequest request, String emailUser);
    TransaksiKeuanganResponse update(Integer id, TransaksiKeuanganRequest request);
    void delete(Integer id);
}
