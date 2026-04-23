package com.example.gmahk.service.impl;

import com.example.gmahk.dto.request.AbsensiRequest;
import com.example.gmahk.dto.request.BulkAbsensiRequest;
import com.example.gmahk.dto.response.AbsensiResponse;
import com.example.gmahk.entity.Absensi;
import com.example.gmahk.entity.Absensi.StatusHadir;
import com.example.gmahk.entity.Jemaat;
import com.example.gmahk.entity.Kegiatan;
import com.example.gmahk.repository.AbsensiRepository;
import com.example.gmahk.repository.JemaatRepository;
import com.example.gmahk.repository.KegiatanRepository;
import com.example.gmahk.service.AbsensiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbsensiServiceImpl implements AbsensiService {

    private final AbsensiRepository repo;
    private final KegiatanRepository kegiatanRepo;
    private final JemaatRepository jemaatRepo;

    @Override
    public List<AbsensiResponse> findByKegiatan(Integer kegiatanId) {
        return repo.findByKegiatanId(kegiatanId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AbsensiResponse> findByJemaat(Integer jemaatId) {
        return repo.findByJemaatId(jemaatId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AbsensiResponse save(AbsensiRequest request) {
        Kegiatan kegiatan = kegiatanRepo.findById(request.getKegiatanId())
                .orElseThrow(() -> new RuntimeException("Kegiatan tidak ditemukan"));
        Jemaat jemaat = jemaatRepo.findById(request.getJemaatId())
                .orElseThrow(() -> new RuntimeException("Jemaat tidak ditemukan"));

        // Update jika sudah ada
        Absensi absensi = repo.findByKegiatanIdAndJemaatId(request.getKegiatanId(), request.getJemaatId())
                .orElse(Absensi.builder().kegiatan(kegiatan).jemaat(jemaat).build());
        absensi.setStatusHadir(StatusHadir.valueOf(request.getStatusHadir()));
        return toResponse(repo.save(absensi));
    }

    @Override
    @Transactional
    public List<AbsensiResponse> saveBulk(BulkAbsensiRequest request) {
        Kegiatan kegiatan = kegiatanRepo.findById(request.getKegiatanId())
                .orElseThrow(() -> new RuntimeException("Kegiatan tidak ditemukan"));

        return request.getAbsensiList().stream().map(item -> {
            Jemaat jemaat = jemaatRepo.findById(item.getJemaatId())
                    .orElseThrow(() -> new RuntimeException("Jemaat tidak ditemukan: " + item.getJemaatId()));
            Absensi absensi = repo.findByKegiatanIdAndJemaatId(request.getKegiatanId(), item.getJemaatId())
                    .orElse(Absensi.builder().kegiatan(kegiatan).jemaat(jemaat).build());
            absensi.setStatusHadir(StatusHadir.valueOf(item.getStatusHadir()));
            return toResponse(repo.save(absensi));
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AbsensiResponse update(Integer id, String statusHadir) {
        Absensi a = repo.findById(id).orElseThrow(() -> new RuntimeException("Absensi tidak ditemukan: " + id));
        a.setStatusHadir(StatusHadir.valueOf(statusHadir));
        return toResponse(repo.save(a));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!repo.existsById(id)) throw new RuntimeException("Absensi tidak ditemukan: " + id);
        repo.deleteById(id);
    }

    @Override
    public long countHadir(Integer kegiatanId) {
        return repo.countHadirByKegiatan(kegiatanId);
    }

    private AbsensiResponse toResponse(Absensi a) {
        return AbsensiResponse.builder()
                .id(a.getId())
                .kegiatanId(a.getKegiatan().getId())
                .namaKegiatan(a.getKegiatan().getNamaKegiatan())
                .jemaatId(a.getJemaat().getId())
                .namaJemaat(a.getJemaat().getNama())
                .statusHadir(a.getStatusHadir().name())
                .build();
    }
}
