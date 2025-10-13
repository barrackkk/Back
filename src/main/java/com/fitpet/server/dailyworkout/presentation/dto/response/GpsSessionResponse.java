package com.fitpet.server.dailyworkout.presentation.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GpsSessionResponse {
    private Long sessionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal totalDistance;
    private BigDecimal avgSpeed;
    private Integer stepCount;
    private Integer burnCalories;
    private List<GpsLogResponse> path; // 달리기 경로 좌표 목록
}