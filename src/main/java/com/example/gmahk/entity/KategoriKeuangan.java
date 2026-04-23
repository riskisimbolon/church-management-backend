package com.example.gmahk.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kategori_keuangan")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class KategoriKeuangan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama_kategori", length = 100)
    private String namaKategori;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('pemasukan','pengeluaran')")
    private TipeKategori tipe;

    public enum TipeKategori { pemasukan, pengeluaran }
}
