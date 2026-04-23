package com.example.gmahk.dto.response;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class KegiatanResponse {
    private Integer id;
    private String namaKegiatan;
    private LocalDate tanggal;
    private String deskripsi;
    private List<KegiatanBagianResponse> bagianList;
}
