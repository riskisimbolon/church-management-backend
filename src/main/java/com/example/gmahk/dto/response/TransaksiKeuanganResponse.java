package com.example.gmahk.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class TransaksiKeuanganResponse {
    private Integer id;
    private LocalDate tanggal;
    private String jenisTransaksi;
    private Integer kategoriId;
    private String namaKategori;
    private BigDecimal jumlah;
    private String keterangan;
    private Integer createdById;
    private String createdByNama;
}
