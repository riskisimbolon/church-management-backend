package com.example.gmahk.dto.response;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class KategoriKeuanganResponse {
    private Integer id;
    private String namaKategori;
    private String tipe;
}
