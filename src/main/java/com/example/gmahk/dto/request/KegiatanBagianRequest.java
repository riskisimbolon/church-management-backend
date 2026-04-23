package com.example.gmahk.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;

@Getter @Setter
public class KegiatanBagianRequest {

    // wajib saat POST, opsional saat PUT (diabaikan di update)
    private Integer kegiatanId;

    @NotBlank(message = "Nama bagian tidak boleh kosong")
    private String namaBagian;

    private Integer urutan;

    private List<KegiatanPetugasRequest> petugasList;
}
