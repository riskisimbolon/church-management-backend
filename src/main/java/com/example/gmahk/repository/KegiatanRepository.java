package com.example.gmahk.repository;

import com.example.gmahk.entity.Kegiatan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface KegiatanRepository extends JpaRepository<Kegiatan, Integer> {
    List<Kegiatan> findByTanggalBetween(LocalDate from, LocalDate to);
    List<Kegiatan> findByNamaKegiatanContainingIgnoreCase(String nama);
}
