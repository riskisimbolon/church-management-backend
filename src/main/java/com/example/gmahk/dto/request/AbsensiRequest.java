package com.example.gmahk.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
public class AbsensiRequest {
    @NotNull(message = "Kegiatan tidak boleh kosong")
    private Integer kegiatanId;
    @NotNull(message = "Jemaat tidak boleh kosong")
    private Integer jemaatId;
    @NotNull(message = "Status hadir tidak boleh kosong")
    private String statusHadir; // hadir / tidak
}
