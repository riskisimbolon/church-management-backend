package com.example.gmahk.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
public class KategoriKeuanganRequest {
    @NotBlank(message = "Nama kategori tidak boleh kosong")
    private String namaKategori;
    @NotNull(message = "Tipe tidak boleh kosong")
    private String tipe; // pemasukan / pengeluaran
}
