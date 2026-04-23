package com.example.gmahk.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
public class KeluargaRequest {
    @NotBlank(message = "Nama keluarga tidak boleh kosong")
    private String namaKeluarga;
    private String alamat;
}
