package com.example.gmahk.dto.response;

import lombok.*;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class KegiatanBagianResponse {
    private Integer id;
    private String namaBagian;
    private Integer urutan;
    private List<KegiatanPetugasResponse> petugasList;
}
