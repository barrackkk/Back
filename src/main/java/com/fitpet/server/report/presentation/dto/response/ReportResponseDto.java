package com.fitpet.server.report.presentation.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class ReportResponseDto {

    // 오늘의 운동 조회
    @Getter
    @Builder
    public static class TodayWorkoutResponse {
        private Long sessionId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private BigDecimal totalDistance;
        private BigDecimal avgSpeed;
        private Integer stepCount;
        private Integer burnCalories;
    }

    // 주간 걸음량 요약
    @Getter
    @Builder
    public static class DailyWalkSummaryResponse {
        private LocalDate date;
        private Integer steps;
        private BigDecimal distanceKm;
        private Integer burnCalories;
    }
}