package com.example.gmahk.service.impl;


import com.example.gmahk.dto.request.TransaksiKeuanganRequest;
import com.example.gmahk.dto.response.KeuanganSummaryResponse;
import com.example.gmahk.dto.response.TransaksiKeuanganResponse;
import com.example.gmahk.entity.KategoriKeuangan;
import com.example.gmahk.entity.TransaksiKeuangan.JenisTransaksi;
import com.example.gmahk.entity.TransaksiKeuangan;
import com.example.gmahk.entity.User;
import com.example.gmahk.repository.KategoriKeuanganRepository;
import com.example.gmahk.repository.TransaksiKeuanganRepository;
import com.example.gmahk.repository.UserRepository;
import com.example.gmahk.service.TransaksiKeuanganService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransaksiKeuanganServiceImpl implements TransaksiKeuanganService {

    private final TransaksiKeuanganRepository repo;
    private final KategoriKeuanganRepository kategoriRepo;
    private final UserRepository userRepo;

    @Override
    public List<TransaksiKeuanganResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public TransaksiKeuanganResponse findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    public List<TransaksiKeuanganResponse> findByTanggal(LocalDate from, LocalDate to) {
        return repo.findByTanggalBetween(from, to).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<TransaksiKeuanganResponse> findByJenis(String jenis) {
        return repo.findByJenisTransaksi(JenisTransaksi.valueOf(jenis)).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public KeuanganSummaryResponse getSummary() {
        BigDecimal pemasukan = repo.totalPemasukan();
        BigDecimal pengeluaran = repo.totalPengeluaran();
        return KeuanganSummaryResponse.builder()
                .totalPemasukan(pemasukan)
                .totalPengeluaran(pengeluaran)
                .saldo(pemasukan.subtract(pengeluaran))
                .build();
    }

    @Override
    @Transactional
    public TransaksiKeuanganResponse save(TransaksiKeuanganRequest request, String emailUser) {
        KategoriKeuangan kategori = kategoriRepo.findById(request.getKategoriId())
                .orElseThrow(() -> new RuntimeException("Kategori tidak ditemukan"));
        User user = userRepo.findByEmail(emailUser)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        TransaksiKeuangan t = TransaksiKeuangan.builder()
                .tanggal(request.getTanggal())
                .jenisTransaksi(JenisTransaksi.valueOf(request.getJenisTransaksi()))
                .kategori(kategori)
                .jumlah(request.getJumlah())
                .keterangan(request.getKeterangan())
                .createdBy(user)
                .build();
        return toResponse(repo.save(t));
    }

    @Override
    @Transactional
    public TransaksiKeuanganResponse update(Integer id, TransaksiKeuanganRequest request) {
        TransaksiKeuangan t = getOrThrow(id);
        KategoriKeuangan kategori = kategoriRepo.findById(request.getKategoriId())
                .orElseThrow(() -> new RuntimeException("Kategori tidak ditemukan"));
        t.setTanggal(request.getTanggal());
        t.setJenisTransaksi(JenisTransaksi.valueOf(request.getJenisTransaksi()));
        t.setKategori(kategori);
        t.setJumlah(request.getJumlah());
        t.setKeterangan(request.getKeterangan());
        return toResponse(repo.save(t));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!repo.existsById(id)) throw new RuntimeException("Transaksi tidak ditemukan: " + id);
        repo.deleteById(id);
    }

    private TransaksiKeuangan getOrThrow(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan: " + id));
    }

    private TransaksiKeuanganResponse toResponse(TransaksiKeuangan t) {
        return TransaksiKeuanganResponse.builder()
                .id(t.getId()).tanggal(t.getTanggal())
                .jenisTransaksi(t.getJenisTransaksi().name())
                .kategoriId(t.getKategori() != null ? t.getKategori().getId() : null)
                .namaKategori(t.getKategori() != null ? t.getKategori().getNamaKategori() : null)
                .jumlah(t.getJumlah()).keterangan(t.getKeterangan())
                .createdById(t.getCreatedBy() != null ? t.getCreatedBy().getId() : null)
                .createdByNama(t.getCreatedBy() != null ? t.getCreatedBy().getNama() : null)
                .build();
    }
}
