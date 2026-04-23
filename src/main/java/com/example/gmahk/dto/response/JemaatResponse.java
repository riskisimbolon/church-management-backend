package com.example.gmahk.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class JemaatResponse {
    private Integer id;
    private String nama;
    private String alamat;
    private String noHp;
    private String status;
    private Integer keluargaId;
    private String namaKeluarga;
    private String statusBaptis;
    private LocalDate tanggalLahir;
}
