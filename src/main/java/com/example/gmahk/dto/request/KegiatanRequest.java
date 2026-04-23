package com.example.gmahk.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class KegiatanRequest {

    @NotBlank(message = "Nama kegiatan tidak boleh kosong")
    private String namaKegiatan;

    @NotNull(message = "Tanggal tidak boleh kosong")
    private LocalDate tanggal;

    private String deskripsi;

    // Nested DTO khusus untuk create/update kegiatan sekaligus dengan bagian
    // kegiatanId TIDAK diperlukan di sini karena diambil dari kegiatan yang dibuat
    private List<BagianDto> bagianList;

    @Getter @Setter
    public static class BagianDto {
        @NotBlank(message = "Nama bagian tidak boleh kosong")
        private String namaBagian;
        private Integer urutan;
        private List<PetugasDto> petugasList;
    }

    @Getter @Setter
    public static class PetugasDto {
        private Integer jemaatId;
        private String namaManual;
    }
}
