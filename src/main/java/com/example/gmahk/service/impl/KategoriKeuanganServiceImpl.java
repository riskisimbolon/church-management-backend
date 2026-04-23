package com.example.gmahk.service.impl;

import com.example.gmahk.dto.request.KategoriKeuanganRequest;
import com.example.gmahk.dto.response.KategoriKeuanganResponse;
import com.example.gmahk.entity.KategoriKeuangan;
import com.example.gmahk.entity.KategoriKeuangan.TipeKategori;
import com.example.gmahk.repository.KategoriKeuanganRepository;
import com.example.gmahk.service.KategoriKeuanganService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KategoriKeuanganServiceImpl implements KategoriKeuanganService {

    private final KategoriKeuanganRepository repo;

    @Override
    public List<KategoriKeuanganResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<KategoriKeuanganResponse> findByTipe(String tipe) {
        return repo.findByTipe(TipeKategori.valueOf(tipe)).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public KategoriKeuanganResponse findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    @Transactional
    public KategoriKeuanganResponse save(KategoriKeuanganRequest request) {
        KategoriKeuangan k = KategoriKeuangan.builder()
                .namaKategori(request.getNamaKategori())
                .tipe(TipeKategori.valueOf(request.getTipe()))
                .build();
        return toResponse(repo.save(k));
    }

    @Override
    @Transactional
    public KategoriKeuanganResponse update(Integer id, KategoriKeuanganRequest request) {
        KategoriKeuangan k = getOrThrow(id);
        k.setNamaKategori(request.getNamaKategori());
        k.setTipe(TipeKategori.valueOf(request.getTipe()));
        return toResponse(repo.save(k));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!repo.existsById(id)) throw new RuntimeException("Kategori tidak ditemukan: " + id);
        repo.deleteById(id);
    }

    private KategoriKeuangan getOrThrow(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Kategori tidak ditemukan: " + id));
    }

    private KategoriKeuanganResponse toResponse(KategoriKeuangan k) {
        return KategoriKeuanganResponse.builder()
                .id(k.getId()).namaKategori(k.getNamaKategori()).tipe(k.getTipe().name())
                .build();
    }
}
