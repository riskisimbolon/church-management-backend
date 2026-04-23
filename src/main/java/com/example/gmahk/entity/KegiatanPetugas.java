package com.example.gmahk.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kegiatan_petugas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class KegiatanPetugas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kegiatan_bagian_id")
    private KegiatanBagian kegiatanBagian;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jemaat_id")
    private Jemaat jemaat;

    @Column(name = "nama_manual", length = 100)
    private String namaManual;
}
