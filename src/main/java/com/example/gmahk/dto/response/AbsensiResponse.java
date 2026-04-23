package com.example.gmahk.dto.response;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AbsensiResponse {
    private Integer id;
    private Integer kegiatanId;
    private String namaKegiatan;
    private Integer jemaatId;
    private String namaJemaat;
    private String statusHadir;
}
