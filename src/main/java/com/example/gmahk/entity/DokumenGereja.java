package com.example.gmahk.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dokumen_gereja", indexes = {
    @Index(name = "idx_dokumen_kategori", columnList = "kategori"),
    @Index(name = "idx_dokumen_created",  columnList = "created_at")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DokumenGereja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String judul;

    @Column(columnDefinition = "TEXT")
    private String deskripsi;

    @Column(name = "nama_file", nullable = false, length = 255)
    private String namaFile;

    @Column(name = "tipe_file", nullable = false, length = 100)
    private String tipeFile;   // MIME type

    @Column(nullable = false)
    private Long ukuran;       // bytes

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] data;

    @Column(length = 100)
    private String kategori;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
