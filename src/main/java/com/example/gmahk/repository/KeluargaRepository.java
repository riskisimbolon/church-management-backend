package com.example.gmahk.repository;

import com.example.gmahk.entity.Keluarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KeluargaRepository extends JpaRepository<Keluarga, Integer> {
    List<Keluarga> findByNamaKeluargaContainingIgnoreCase(String nama);
}
