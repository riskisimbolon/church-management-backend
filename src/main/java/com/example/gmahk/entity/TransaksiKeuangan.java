package com.example.gmahk.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaksi_keuangan", indexes = {
    @Index(name = "idx_transaksi_tanggal", columnList = "tanggal")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransaksiKeuangan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate tanggal;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_transaksi", columnDefinition = "ENUM('in','out')")
    private JenisTransaksi jenisTransaksi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kategori_id")
    private KategoriKeuangan kategori;

    @Column(precision = 12, scale = 2)
    private BigDecimal jumlah;

    @Column(columnDefinition = "TEXT")
    private String keterangan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    public enum JenisTransaksi { in, out }
}
