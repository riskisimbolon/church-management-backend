package com.example.gmahk.repository;


import com.example.gmahk.entity.TransaksiKeuangan;
import com.example.gmahk.entity.TransaksiKeuangan.JenisTransaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransaksiKeuanganRepository extends JpaRepository<TransaksiKeuangan, Integer> {
    List<TransaksiKeuangan> findByTanggalBetween(LocalDate from, LocalDate to);
    List<TransaksiKeuangan> findByJenisTransaksi(JenisTransaksi jenis);
    List<TransaksiKeuangan> findByKategoriId(Integer kategoriId);

    @Query("SELECT COALESCE(SUM(t.jumlah), 0) FROM TransaksiKeuangan t WHERE t.jenisTransaksi = 'in'")
    BigDecimal totalPemasukan();

    @Query("SELECT COALESCE(SUM(t.jumlah), 0) FROM TransaksiKeuangan t WHERE t.jenisTransaksi = 'out'")
    BigDecimal totalPengeluaran();
}
