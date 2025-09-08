package com.fitpet.server.dailywalk.presentation.dto.response;

import java.time.LocalDateTime;
import com.fitpet.server.dailywalk.domain.entity.DailyWalk;

public record DailyWalkResponse(
        Long id,
        Integer step,
        java.math.BigDecimal distanceKm,
        Integer burnCalories,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static DailyWalkResponse from(DailyWalk e) {
        return new DailyWalkResponse(
                e.getId(),
                e.getStep(),
                e.getDistanceKm(),
                e.getBurnCalories(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
