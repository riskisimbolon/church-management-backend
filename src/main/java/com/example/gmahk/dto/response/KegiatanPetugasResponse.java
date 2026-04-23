package com.example.gmahk.dto.response;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class KegiatanPetugasResponse {
    private Integer id;
    private Integer jemaatId;
    private String namaJemaat;
    private String namaManual;
    private String namaTampil; // namaJemaat atau namaManual
}
