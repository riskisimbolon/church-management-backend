package com.example.gmahk.service.impl;

import com.example.gmahk.dto.response.DokumenGerejaResponse;
import com.example.gmahk.entity.DokumenGereja;
import com.example.gmahk.entity.User;
import com.example.gmahk.repository.DokumenGerejaRepository;
import com.example.gmahk.repository.UserRepository;
import com.example.gmahk.service.DokumenGerejaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DokumenGerejaServiceImpl implements DokumenGerejaService {

    private final DokumenGerejaRepository repo;
    private final UserRepository userRepository;

    // Tipe file yang diizinkan
    private static final Set<String> ALLOWED_TYPES = Set.of(
        "application/pdf",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",      // .xlsx
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .pptx
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"  // .docx
    );

    // Maksimal 20 MB
    private static final long MAX_SIZE = 20 * 1024 * 1024;

    @Override
    public List<DokumenGerejaResponse> findAll() {
        return repo.findAllWithoutData();
    }

    @Override
    public List<DokumenGerejaResponse> findByKategori(String kategori) {
        return repo.findByKategoriWithoutData(kategori);
    }

    @Override
    public List<DokumenGerejaResponse> search(String judul) {
        return repo.findByJudulWithoutData(judul);
    }

    @Override
    public DokumenGerejaResponse findMetaById(Integer id) {
        DokumenGereja d = getOrThrow(id);
        return toResponse(d);
    }

    @Override
    public DokumenGereja findWithDataById(Integer id) {
        return getOrThrow(id);
    }

    @Override
    @Transactional
    public DokumenGerejaResponse upload(MultipartFile file, String judul, String deskripsi,
                                        String kategori, String emailUploader) {
        // Validasi file tidak kosong
        if (file == null || file.isEmpty())
            throw new RuntimeException("File tidak boleh kosong");

        // Validasi ukuran
        if (file.getSize() > MAX_SIZE)
            throw new RuntimeException("Ukuran file maksimal 20 MB");

        // Validasi tipe
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType))
            throw new RuntimeException(
                "Tipe file tidak didukung. Hanya PDF, Excel, PowerPoint, dan Word yang diizinkan"
            );

        User uploader = userRepository.findByEmail(emailUploader)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        try {
            DokumenGereja dokumen = DokumenGereja.builder()
                    .judul(judul.trim())
                    .deskripsi(deskripsi)
                    .namaFile(file.getOriginalFilename())
                    .tipeFile(contentType)
                    .ukuran(file.getSize())
                    .data(file.getBytes())
                    .kategori(kategori)
                    .uploadedBy(uploader)
                    .build();

            DokumenGereja saved = repo.save(dokumen);
            return toResponse(saved);

        } catch (Exception e) {
            throw new RuntimeException("Gagal menyimpan file: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!repo.existsById(id))
            throw new RuntimeException("Dokumen tidak ditemukan: " + id);
        repo.deleteById(id);
    }

    private DokumenGereja getOrThrow(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Dokumen tidak ditemukan: " + id));
    }

    private DokumenGerejaResponse toResponse(DokumenGereja d) {
        return DokumenGerejaResponse.builder()
                .id(d.getId())
                .judul(d.getJudul())
                .deskripsi(d.getDeskripsi())
                .namaFile(d.getNamaFile())
                .tipeFile(d.getTipeFile())
                .ukuran(d.getUkuran())
                .kategori(d.getKategori())
                .uploadedByNama(d.getUploadedBy() != null ? d.getUploadedBy().getNama() : "-")
                .createdAt(d.getCreatedAt())
                .build();
    }
}
