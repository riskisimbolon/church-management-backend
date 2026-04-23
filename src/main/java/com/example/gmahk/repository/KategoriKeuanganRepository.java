package com.example.gmahk.repository;

import com.example.gmahk.entity.KategoriKeuangan;
import com.example.gmahk.entity.KategoriKeuangan.TipeKategori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KategoriKeuanganRepository extends JpaRepository<KategoriKeuangan, Integer> {
    List<KategoriKeuangan> findByTipe(TipeKategori tipe);
}
