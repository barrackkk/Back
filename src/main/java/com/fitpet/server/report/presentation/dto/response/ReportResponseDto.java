package com.fitpet.server.report.presentation.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

public class ReportResponseDto {

    @Getter
    @Builder
    public static class TodayActivityResponse {
        private LocalDate date;
        private Integer steps;
        private BigDecimal distanceKm;
        private Integer burnCalories;
    }

    @Getter
    @Builder
    public static class ActivityRangeResponse {
        private LocalDate date;
        private Integer steps;
        private Integer burnCalories;
        private BigDecimal distanceKm;
    }
}