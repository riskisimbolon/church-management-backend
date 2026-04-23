package com.example.gmahk.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class KeuanganSummaryResponse {
    private BigDecimal totalPemasukan;
    private BigDecimal totalPengeluaran;
    private BigDecimal saldo;
}
