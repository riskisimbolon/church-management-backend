package com.example.gmahk.repository;

import com.example.gmahk.entity.DokumenGereja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DokumenGerejaRepository extends JpaRepository<DokumenGereja, Integer> {

    List<DokumenGereja> findByKategoriIgnoreCaseOrderByCreatedAtDesc(String kategori);

    List<DokumenGereja> findAllByOrderByCreatedAtDesc();

    List<DokumenGereja> findByJudulContainingIgnoreCaseOrderByCreatedAtDesc(String judul);

    // List tanpa kolom data (BLOB) agar tidak boros memori saat list
    @Query("""
        SELECT new com.example.gmahk.dto.response.DokumenGerejaResponse(
            d.id, d.judul, d.deskripsi, d.namaFile,
            d.tipeFile, d.ukuran, d.kategori,
            d.uploadedBy.nama, d.createdAt
        )
        FROM DokumenGereja d
        ORDER BY d.createdAt DESC
    """)
    List<com.example.gmahk.dto.response.DokumenGerejaResponse> findAllWithoutData();

    @Query("""
        SELECT new com.example.gmahk.dto.response.DokumenGerejaResponse(
            d.id, d.judul, d.deskripsi, d.namaFile,
            d.tipeFile, d.ukuran, d.kategori,
            d.uploadedBy.nama, d.createdAt
        )
        FROM DokumenGereja d
        WHERE LOWER(d.kategori) = LOWER(:kategori)
        ORDER BY d.createdAt DESC
    """)
    List<com.example.gmahk.dto.response.DokumenGerejaResponse> findByKategoriWithoutData(String kategori);

    @Query("""
        SELECT new com.example.gmahk.dto.response.DokumenGerejaResponse(
            d.id, d.judul, d.deskripsi, d.namaFile,
            d.tipeFile, d.ukuran, d.kategori,
            d.uploadedBy.nama, d.createdAt
        )
        FROM DokumenGereja d
        WHERE LOWER(d.judul) LIKE LOWER(CONCAT('%', :judul, '%'))
        ORDER BY d.createdAt DESC
    """)
    List<com.example.gmahk.dto.response.DokumenGerejaResponse> findByJudulWithoutData(String judul);
}
