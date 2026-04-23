package com.example.gmahk.service;


import com.example.gmahk.dto.request.JemaatRequest;
import com.example.gmahk.dto.response.JemaatResponse;

import java.util.List;

public interface JemaatService {
    List<JemaatResponse> findAll();
    JemaatResponse findById(Integer id);
    List<JemaatResponse> findByNama(String nama);
    List<JemaatResponse> findByStatus(String status);
    JemaatResponse save(JemaatRequest request);
    JemaatResponse update(Integer id, JemaatRequest request);
    List<JemaatResponse> findBirthdayToday();
    List<JemaatResponse> findBirthdayThisMonth();
    void delete(Integer id);
}
