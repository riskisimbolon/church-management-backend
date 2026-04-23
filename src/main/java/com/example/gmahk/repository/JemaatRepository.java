package com.example.gmahk.repository;


import com.example.gmahk.entity.Jemaat;
import com.example.gmahk.entity.Jemaat.StatusJemaat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JemaatRepository extends JpaRepository<Jemaat, Integer> {
    List<Jemaat> findByNamaContainingIgnoreCase(String nama);
    List<Jemaat> findByStatus(StatusJemaat status);
    List<Jemaat> findByKeluargaId(Integer keluargaId);
    @Query(value = """
    SELECT * FROM jemaat
    WHERE MONTH(tanggal_lahir) = MONTH(CURDATE())
    AND DAY(tanggal_lahir) = DAY(CURDATE())
    """, nativeQuery = true)
    List<Jemaat> findBirthdayToday();
    @Query(value = """
    SELECT * FROM jemaat
    WHERE MONTH(tanggal_lahir) = MONTH(CURDATE())
    ORDER BY DAY(tanggal_lahir)
    """, nativeQuery = true)
    List<Jemaat> findBirthdayThisMonth();
}
