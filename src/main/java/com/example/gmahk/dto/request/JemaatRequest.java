package com.example.gmahk.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
public class JemaatRequest {
    @NotBlank(message = "Nama tidak boleh kosong")
    private String nama;
    private String alamat;
    private String noHp;
    private String status;
    private Integer keluargaId;
    private String statusBaptis;
    private LocalDate tanggalLahir;
}
