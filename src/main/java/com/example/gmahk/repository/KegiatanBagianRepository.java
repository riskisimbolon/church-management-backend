package com.example.gmahk.repository;


import com.example.gmahk.entity.KegiatanBagian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KegiatanBagianRepository extends JpaRepository<KegiatanBagian, Integer> {
    List<KegiatanBagian> findByKegiatanIdOrderByUrutan(Integer kegiatanId);
}
