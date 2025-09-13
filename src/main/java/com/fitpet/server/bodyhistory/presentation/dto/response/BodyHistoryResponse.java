package com.fitpet.server.bodyhistory.presentation.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record BodyHistoryResponse(
        Long id,
        Long userId,
        BigDecimal heightCm,
        BigDecimal weightKg,
        BigDecimal pbf,
        LocalDate baseDate,
        LocalDateTime createdAt
) {
}