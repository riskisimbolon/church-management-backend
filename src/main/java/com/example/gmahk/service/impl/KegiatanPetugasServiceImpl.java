package com.example.gmahk.service.impl;

import com.example.gmahk.dto.request.KegiatanPetugasRequest;
import com.example.gmahk.dto.response.KegiatanPetugasResponse;
import com.example.gmahk.entity.Jemaat;
import com.example.gmahk.entity.KegiatanBagian;
import com.example.gmahk.entity.KegiatanPetugas;
import com.example.gmahk.repository.JemaatRepository;
import com.example.gmahk.repository.KegiatanBagianRepository;
import com.example.gmahk.repository.KegiatanPetugasRepository;
import com.example.gmahk.service.KegiatanPetugasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KegiatanPetugasServiceImpl implements KegiatanPetugasService {

    private final KegiatanPetugasRepository petugasRepo;
    private final KegiatanBagianRepository bagianRepo;
    private final JemaatRepository jemaatRepo;

    @Override
    public List<KegiatanPetugasResponse> findByBagian(Integer bagianId) {
        return petugasRepo.findByKegiatanBagianId(bagianId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public KegiatanPetugasResponse save(KegiatanPetugasRequest request) {
        if (request.getKegiatanBagianId() == null)
            throw new RuntimeException("kegiatanBagianId wajib diisi");

        if (request.getJemaatId() == null
                && (request.getNamaManual() == null || request.getNamaManual().isBlank()))
            throw new RuntimeException("Harus isi salah satu: jemaatId atau namaManual");

        KegiatanBagian bagian = bagianRepo.findById(request.getKegiatanBagianId())
                .orElseThrow(() -> new RuntimeException("Bagian tidak ditemukan: " + request.getKegiatanBagianId()));

        Jemaat jemaat = null;
        if (request.getJemaatId() != null) {
            jemaat = jemaatRepo.findById(request.getJemaatId())
                    .orElseThrow(() -> new RuntimeException("Jemaat tidak ditemukan: " + request.getJemaatId()));
        }

        KegiatanPetugas petugas = KegiatanPetugas.builder()
                .kegiatanBagian(bagian)
                .jemaat(jemaat)
                .namaManual(jemaat == null ? request.getNamaManual() : null)
                .build();

        return toResponse(petugasRepo.save(petugas));
    }

    @Override
    @Transactional
    public KegiatanPetugasResponse update(Integer id, KegiatanPetugasRequest request) {
        KegiatanPetugas petugas = petugasRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Petugas tidak ditemukan: " + id));

        if (request.getJemaatId() == null
                && (request.getNamaManual() == null || request.getNamaManual().isBlank()))
            throw new RuntimeException("Harus isi salah satu: jemaatId atau namaManual");

        Jemaat jemaat = null;
        if (request.getJemaatId() != null) {
            jemaat = jemaatRepo.findById(request.getJemaatId())
                    .orElseThrow(() -> new RuntimeException("Jemaat tidak ditemukan: " + request.getJemaatId()));
        }

        petugas.setJemaat(jemaat);
        petugas.setNamaManual(jemaat == null ? request.getNamaManual() : null);

        return toResponse(petugasRepo.save(petugas));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!petugasRepo.existsById(id))
            throw new RuntimeException("Petugas tidak ditemukan: " + id);
        petugasRepo.deleteById(id);
    }

    private KegiatanPetugasResponse toResponse(KegiatanPetugas p) {
        return KegiatanPetugasResponse.builder()
                .id(p.getId())
                .jemaatId(p.getJemaat() != null ? p.getJemaat().getId() : null)
                .namaJemaat(p.getJemaat() != null ? p.getJemaat().getNama() : null)
                .namaManual(p.getNamaManual())
                .namaTampil(p.getJemaat() != null ? p.getJemaat().getNama() : p.getNamaManual())
                .build();
    }
}
