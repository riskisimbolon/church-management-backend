package com.example.gmahk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "jemaat", indexes = {
    @Index(name = "idx_jemaat_nama", columnList = "nama")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Jemaat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nama;

    @Column(columnDefinition = "TEXT")
    private String alamat;

    @Column(name = "no_hp", length = 20)
    private String noHp;

    public String getStatusBaptis() {
        return statusBaptis;
    }

    public void setStatusBaptis(String statusBaptis) {
        this.statusBaptis = statusBaptis;
    }

    @Column(name = "status_baptis", length = 20)
    private String statusBaptis = "belum";

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    @Column(name = "tanggal_lahir")
    private LocalDate tanggalLahir;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('aktif','pindah','meninggal') DEFAULT 'aktif'")
    @Builder.Default
    private StatusJemaat status = StatusJemaat.aktif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keluarga_id")
    private Keluarga keluarga;

    public enum StatusJemaat { aktif, pindah, meninggal }
}
