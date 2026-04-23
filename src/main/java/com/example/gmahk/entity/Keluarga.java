package com.example.gmahk.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "keluarga")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Keluarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama_keluarga", length = 100)
    private String namaKeluarga;

    @Column(columnDefinition = "TEXT")
    private String alamat;
}
