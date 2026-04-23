package com.example.gmahk.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class TransaksiKeuanganRequest {
    @NotNull(message = "Tanggal tidak boleh kosong")
    private LocalDate tanggal;
    @NotNull(message = "Jenis transaksi tidak boleh kosong")
    private String jenisTransaksi; // in / out
    @NotNull(message = "Kategori tidak boleh kosong")
    private Integer kategoriId;
    @NotNull @Positive(message = "Jumlah harus lebih dari 0")
    private BigDecimal jumlah;
    private String keterangan;
}
