package com.example.gmahk.repository;

import com.example.gmahk.entity.KegiatanPetugas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KegiatanPetugasRepository extends JpaRepository<KegiatanPetugas, Integer> {
    List<KegiatanPetugas> findByKegiatanBagianId(Integer bagianId);
    List<KegiatanPetugas> findByJemaatId(Integer jemaatId);
}
