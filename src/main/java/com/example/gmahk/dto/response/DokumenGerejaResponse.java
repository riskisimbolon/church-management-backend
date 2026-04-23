package com.example.gmahk.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DokumenGerejaResponse {

    private Integer id;
    private String judul;
    private String deskripsi;
    private String namaFile;
    private String tipeFile;
    private Long ukuran;
    private String kategori;
    private String uploadedByNama;
    private LocalDateTime createdAt;

    // Format ukuran file yang mudah dibaca (KB / MB)
    public String getUkuranFormatted() {
        if (ukuran == null) return "-";
        if (ukuran < 1024) return ukuran + " B";
        if (ukuran < 1024 * 1024) return String.format("%.1f KB", ukuran / 1024.0);
        return String.format("%.1f MB", ukuran / (1024.0 * 1024));
    }
}
