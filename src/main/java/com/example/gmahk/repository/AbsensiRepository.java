package com.example.gmahk.repository;


import com.example.gmahk.entity.Absensi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AbsensiRepository extends JpaRepository<Absensi, Integer> {
    List<Absensi> findByKegiatanId(Integer kegiatanId);
    List<Absensi> findByJemaatId(Integer jemaatId);
    Optional<Absensi> findByKegiatanIdAndJemaatId(Integer kegiatanId, Integer jemaatId);

    @Query("SELECT COUNT(a) FROM Absensi a WHERE a.kegiatan.id = :kegiatanId AND a.statusHadir = 'hadir'")
    long countHadirByKegiatan(Integer kegiatanId);
}
