package com.example.gmahk.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "kegiatan", indexes = {
    @Index(name = "idx_kegiatan_tanggal", columnList = "tanggal")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Kegiatan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama_kegiatan", length = 100)
    private String namaKegiatan;

    private LocalDate tanggal;

    @Column(columnDefinition = "TEXT")
    private String deskripsi;

    @OneToMany(mappedBy = "kegiatan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KegiatanBagian> bagianList;
}
