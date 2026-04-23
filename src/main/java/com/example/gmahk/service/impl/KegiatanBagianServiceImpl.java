package com.example.gmahk.service.impl;

import com.example.gmahk.dto.request.KegiatanBagianRequest;
import com.example.gmahk.dto.request.KegiatanPetugasRequest;
import com.example.gmahk.dto.response.KegiatanBagianResponse;
import com.example.gmahk.dto.response.KegiatanPetugasResponse;
import com.example.gmahk.entity.Jemaat;
import com.example.gmahk.entity.Kegiatan;
import com.example.gmahk.entity.KegiatanBagian;
import com.example.gmahk.entity.KegiatanPetugas;
import com.example.gmahk.repository.JemaatRepository;
import com.example.gmahk.repository.KegiatanBagianRepository;
import com.example.gmahk.repository.KegiatanPetugasRepository;
import com.example.gmahk.repository.KegiatanRepository;
import com.example.gmahk.service.KegiatanBagianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KegiatanBagianServiceImpl implements KegiatanBagianService {

    private final KegiatanBagianRepository bagianRepo;
    private final KegiatanPetugasRepository petugasRepo;
    private final KegiatanRepository kegiatanRepo;
    private final JemaatRepository jemaatRepo;

    @Override
    public List<KegiatanBagianResponse> findByKegiatan(Integer kegiatanId) {
        return bagianRepo.findByKegiatanIdOrderByUrutan(kegiatanId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public KegiatanBagianResponse findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    @Transactional
    public KegiatanBagianResponse save(KegiatanBagianRequest request) {
        if (request.getKegiatanId() == null)
            throw new RuntimeException("kegiatanId wajib diisi");

        Kegiatan kegiatan = kegiatanRepo.findById(request.getKegiatanId())
                .orElseThrow(() -> new RuntimeException("Kegiatan tidak ditemukan: " + request.getKegiatanId()));

        int urutan = request.getUrutan() != null
                ? request.getUrutan()
                : bagianRepo.findByKegiatanIdOrderByUrutan(request.getKegiatanId()).size() + 1;

        KegiatanBagian bagian = KegiatanBagian.builder()
                .kegiatan(kegiatan)
                .namaBagian(request.getNamaBagian())
                .urutan(urutan)
                .petugasList(new ArrayList<>())
                .build();

        KegiatanBagian saved = bagianRepo.save(bagian);

        if (request.getPetugasList() != null) {
            for (KegiatanPetugasRequest pr : request.getPetugasList()) {
                Jemaat jemaat = pr.getJemaatId() != null
                        ? jemaatRepo.findById(pr.getJemaatId()).orElse(null) : null;
                petugasRepo.save(KegiatanPetugas.builder()
                        .kegiatanBagian(saved)
                        .jemaat(jemaat)
                        .namaManual(jemaat == null ? pr.getNamaManual() : null)
                        .build());
            }
        }

        return toResponse(bagianRepo.findById(saved.getId()).orElseThrow());
    }

    @Override
    @Transactional
    public KegiatanBagianResponse update(Integer id, KegiatanBagianRequest request) {
        KegiatanBagian bagian = getOrThrow(id);
        bagian.setNamaBagian(request.getNamaBagian());
        if (request.getUrutan() != null) bagian.setUrutan(request.getUrutan());
        bagianRepo.save(bagian);
        return toResponse(bagianRepo.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!bagianRepo.existsById(id))
            throw new RuntimeException("Bagian tidak ditemukan: " + id);
        bagianRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void reorder(Integer kegiatanId, List<Integer> orderedIds) {
        for (int i = 0; i < orderedIds.size(); i++) {
            KegiatanBagian b = bagianRepo.findById(orderedIds.get(i))
                    .orElseThrow(() -> new RuntimeException("Bagian tidak ditemukan"));
            b.setUrutan(i + 1);
            bagianRepo.save(b);
        }
    }

    // ── private helpers ───────────────────────────────────────────────────────

    private KegiatanBagian getOrThrow(Integer id) {
        return bagianRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bagian tidak ditemukan: " + id));
    }

    private KegiatanBagianResponse toResponse(KegiatanBagian b) {
        List<KegiatanPetugasResponse> petugasList = petugasRepo.findByKegiatanBagianId(b.getId())
                .stream()
                .map(p -> KegiatanPetugasResponse.builder()
                        .id(p.getId())
                        .jemaatId(p.getJemaat() != null ? p.getJemaat().getId() : null)
                        .namaJemaat(p.getJemaat() != null ? p.getJemaat().getNama() : null)
                        .namaManual(p.getNamaManual())
                        .namaTampil(p.getJemaat() != null ? p.getJemaat().getNama() : p.getNamaManual())
                        .build())
                .collect(Collectors.toList());

        return KegiatanBagianResponse.builder()
                .id(b.getId())
                .namaBagian(b.getNamaBagian())
                .urutan(b.getUrutan())
                .petugasList(petugasList)
                .build();
    }
}
