package com.example.gmahk.service.impl;


import com.example.gmahk.dto.request.KeluargaRequest;
import com.example.gmahk.dto.response.KeluargaResponse;
import com.example.gmahk.entity.Keluarga;
import com.example.gmahk.repository.KeluargaRepository;
import com.example.gmahk.service.KeluargaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeluargaServiceImpl implements KeluargaService {

    private final KeluargaRepository repo;

    @Override
    public List<KeluargaResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public KeluargaResponse findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    @Transactional
    public KeluargaResponse save(KeluargaRequest request) {
        Keluarga k = Keluarga.builder()
                .namaKeluarga(request.getNamaKeluarga())
                .alamat(request.getAlamat())
                .build();
        return toResponse(repo.save(k));
    }

    @Override
    @Transactional
    public KeluargaResponse update(Integer id, KeluargaRequest request) {
        Keluarga k = getOrThrow(id);
        k.setNamaKeluarga(request.getNamaKeluarga());
        k.setAlamat(request.getAlamat());
        return toResponse(repo.save(k));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!repo.existsById(id)) throw new RuntimeException("Keluarga tidak ditemukan: " + id);
        repo.deleteById(id);
    }

    private Keluarga getOrThrow(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Keluarga tidak ditemukan: " + id));
    }

    private KeluargaResponse toResponse(Keluarga k) {
        return KeluargaResponse.builder()
                .id(k.getId()).namaKeluarga(k.getNamaKeluarga()).alamat(k.getAlamat())
                .build();
    }
}
