package com.example.gmahk.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "kegiatan_bagian")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class KegiatanBagian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kegiatan_id")
    private Kegiatan kegiatan;

    @Column(name = "nama_bagian", length = 100)
    private String namaBagian;

    private Integer urutan;

    @OneToMany(mappedBy = "kegiatanBagian", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KegiatanPetugas> petugasList;
}
