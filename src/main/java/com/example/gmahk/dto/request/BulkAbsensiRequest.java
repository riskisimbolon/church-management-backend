package com.example.gmahk.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Getter @Setter
public class BulkAbsensiRequest {
    @NotNull
    private Integer kegiatanId;
    @NotNull
    private List<AbsensiItemRequest> absensiList;

    @Getter @Setter
    public static class AbsensiItemRequest {
        private Integer jemaatId;
        private String statusHadir;
    }
}
