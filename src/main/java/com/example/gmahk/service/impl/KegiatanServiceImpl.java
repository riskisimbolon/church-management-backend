package com.example.gmahk.service.impl;

import com.example.gmahk.dto.request.KegiatanRequest;
import com.example.gmahk.dto.response.KegiatanBagianResponse;
import com.example.gmahk.dto.response.KegiatanPetugasResponse;
import com.example.gmahk.dto.response.KegiatanResponse;
import com.example.gmahk.entity.Jemaat;
import com.example.gmahk.entity.Kegiatan;
import com.example.gmahk.entity.KegiatanBagian;
import com.example.gmahk.entity.KegiatanPetugas;
import com.example.gmahk.repository.JemaatRepository;
import com.example.gmahk.repository.KegiatanBagianRepository;
import com.example.gmahk.repository.KegiatanPetugasRepository;
import com.example.gmahk.repository.KegiatanRepository;
import com.example.gmahk.service.KegiatanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KegiatanServiceImpl implements KegiatanService {

    private final KegiatanRepository kegiatanRepo;
    private final KegiatanBagianRepository bagianRepo;
    private final KegiatanPetugasRepository petugasRepo;
    private final JemaatRepository jemaatRepo;

    @Override
    public List<KegiatanResponse> findAll() {
        return kegiatanRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public KegiatanResponse findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    @Transactional
    public KegiatanResponse save(KegiatanRequest request) {
        Kegiatan kegiatan = Kegiatan.builder()
                .namaKegiatan(request.getNamaKegiatan())
                .tanggal(request.getTanggal())
                .deskripsi(request.getDeskripsi())
                .bagianList(new ArrayList<>())
                .build();

        Kegiatan saved = kegiatanRepo.save(kegiatan);

        if (request.getBagianList() != null) {
            saveBagianList(saved, request.getBagianList());
        }

        return toResponse(kegiatanRepo.findById(saved.getId()).orElseThrow());
    }

    @Override
    @Transactional
    public KegiatanResponse update(Integer id, KegiatanRequest request) {
        Kegiatan kegiatan = getOrThrow(id);
        kegiatan.setNamaKegiatan(request.getNamaKegiatan());
        kegiatan.setTanggal(request.getTanggal());
        kegiatan.setDeskripsi(request.getDeskripsi());

        if (request.getBagianList() != null) {
            kegiatan.getBagianList().clear();
            kegiatanRepo.saveAndFlush(kegiatan);
            saveBagianList(kegiatan, request.getBagianList());
        } else {
            kegiatanRepo.save(kegiatan);
        }

        return toResponse(kegiatanRepo.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!kegiatanRepo.existsById(id))
            throw new RuntimeException("Kegiatan tidak ditemukan: " + id);
        kegiatanRepo.deleteById(id);
    }


    private void saveBagianList(Kegiatan kegiatan, List<KegiatanRequest.BagianDto> bagianDtoList) {
        for (int i = 0; i < bagianDtoList.size(); i++) {
            KegiatanRequest.BagianDto dto = bagianDtoList.get(i);

            KegiatanBagian bagian = KegiatanBagian.builder()
                    .kegiatan(kegiatan)
                    .namaBagian(dto.getNamaBagian())
                    .urutan(dto.getUrutan() != null ? dto.getUrutan() : i + 1)
                    .petugasList(new ArrayList<>())
                    .build();

            KegiatanBagian savedBagian = bagianRepo.save(bagian);

            if (dto.getPetugasList() != null) {
                for (KegiatanRequest.PetugasDto pd : dto.getPetugasList()) {
                    Jemaat jemaat = pd.getJemaatId() != null
                            ? jemaatRepo.findById(pd.getJemaatId()).orElse(null)
                            : null;
                    petugasRepo.save(KegiatanPetugas.builder()
                            .kegiatanBagian(savedBagian)
                            .jemaat(jemaat)
                            .namaManual(jemaat == null ? pd.getNamaManual() : null)
                            .build());
                }
            }
        }
    }

    private Kegiatan getOrThrow(Integer id) {
        return kegiatanRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Kegiatan tidak ditemukan: " + id));
    }

    private KegiatanResponse toResponse(Kegiatan k) {
        List<KegiatanBagianResponse> bagianResponses = bagianRepo
                .findByKegiatanIdOrderByUrutan(k.getId())
                .stream()
                .map(b -> {
                    List<KegiatanPetugasResponse> petugasResponses = petugasRepo
                            .findByKegiatanBagianId(b.getId())
                            .stream()
                            .map(p -> KegiatanPetugasResponse.builder()
                                    .id(p.getId())
                                    .jemaatId(p.getJemaat() != null ? p.getJemaat().getId() : null)
                                    .namaJemaat(p.getJemaat() != null ? p.getJemaat().getNama() : null)
                                    .namaManual(p.getNamaManual())
                                    .namaTampil(p.getJemaat() != null
                                            ? p.getJemaat().getNama()
                                            : p.getNamaManual())
                                    .build())
                            .collect(Collectors.toList());

                    return KegiatanBagianResponse.builder()
                            .id(b.getId())
                            .namaBagian(b.getNamaBagian())
                            .urutan(b.getUrutan())
                            .petugasList(petugasResponses)
                            .build();
                })
                .collect(Collectors.toList());

        return KegiatanResponse.builder()
                .id(k.getId())
                .namaKegiatan(k.getNamaKegiatan())
                .tanggal(k.getTanggal())
                .deskripsi(k.getDeskripsi())
                .bagianList(bagianResponses)
                .build();
    }
}
