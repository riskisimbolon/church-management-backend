package com.example.gmahk.service.impl;

import com.example.gmahk.dto.request.JemaatRequest;
import com.example.gmahk.dto.response.JemaatResponse;
import com.example.gmahk.entity.Jemaat;
import com.example.gmahk.entity.Jemaat.StatusJemaat;
import com.example.gmahk.entity.Keluarga;
import com.example.gmahk.repository.JemaatRepository;
import com.example.gmahk.repository.KeluargaRepository;
import com.example.gmahk.service.JemaatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JemaatServiceImpl implements JemaatService {

    private final JemaatRepository repo;
    private final KeluargaRepository keluargaRepo;

    @Override
    public List<JemaatResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public JemaatResponse findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    public List<JemaatResponse> findByNama(String nama) {
        return repo.findByNamaContainingIgnoreCase(nama).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<JemaatResponse> findByStatus(String status) {
        return repo.findByStatus(StatusJemaat.valueOf(status)).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public JemaatResponse save(JemaatRequest request) {
        Keluarga keluarga = request.getKeluargaId() != null
                ? keluargaRepo.findById(request.getKeluargaId()).orElseThrow(() -> new RuntimeException("Keluarga tidak ditemukan"))
                : null;
        Jemaat j = Jemaat.builder()
                .nama(request.getNama())
                .alamat(request.getAlamat())
                .noHp(request.getNoHp())
                .status(request.getStatus() != null ? StatusJemaat.valueOf(request.getStatus()) : StatusJemaat.aktif)
                .keluarga(keluarga)
                .statusBaptis(request.getStatusBaptis())
                .tanggalLahir(request.getTanggalLahir())
                .build();
        return toResponse(repo.save(j));
    }

    @Override
    @Transactional
    public JemaatResponse update(Integer id, JemaatRequest request) {
        Jemaat j = getOrThrow(id);
        Keluarga keluarga = request.getKeluargaId() != null
                ? keluargaRepo.findById(request.getKeluargaId()).orElseThrow(() -> new RuntimeException("Keluarga tidak ditemukan"))
                : null;
        j.setNama(request.getNama());
        j.setAlamat(request.getAlamat());
        j.setNoHp(request.getNoHp());
        j.setStatus(request.getStatus() != null ? StatusJemaat.valueOf(request.getStatus()) : j.getStatus());
        j.setKeluarga(keluarga);
        j.setStatusBaptis(request.getStatusBaptis());
        j.setTanggalLahir(request.getTanggalLahir());
        return toResponse(repo.save(j));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!repo.existsById(id)) throw new RuntimeException("Jemaat tidak ditemukan: " + id);
        repo.deleteById(id);
    }

    private Jemaat getOrThrow(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Jemaat tidak ditemukan: " + id));
    }

    private JemaatResponse toResponse(Jemaat j) {
        return JemaatResponse.builder()
                .id(j.getId()).nama(j.getNama()).alamat(j.getAlamat()).noHp(j.getNoHp())
                .status(j.getStatus().name())
                .keluargaId(j.getKeluarga() != null ? j.getKeluarga().getId() : null)
                .namaKeluarga(j.getKeluarga() != null ? j.getKeluarga().getNamaKeluarga() : null)
                .statusBaptis(j.getStatusBaptis())
                .tanggalLahir(j.getTanggalLahir())
                .build();
    }

    @Override
    public List<JemaatResponse> findBirthdayToday() {
        return repo.findBirthdayToday()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JemaatResponse> findBirthdayThisMonth() {
        return repo.findBirthdayThisMonth()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
