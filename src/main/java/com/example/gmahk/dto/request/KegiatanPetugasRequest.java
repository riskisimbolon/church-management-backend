package com.example.gmahk.dto.request;

import lombok.*;

@Getter @Setter
public class KegiatanPetugasRequest {

    // wajib saat POST, diabaikan saat PUT
    private Integer kegiatanBagianId;

    // isi salah satu: jemaatId ATAU namaManual
    private Integer jemaatId;
    private String namaManual;
}
