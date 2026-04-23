package com.example.gmahk.dto.response;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class KeluargaResponse {
    private Integer id;
    private String namaKeluarga;
    private String alamat;
}
