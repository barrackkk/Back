package com.fitpet.server.domain.dailywalk.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

public record DailyWalkCreateRequest(
        @NotNull Long userId,

        @NotNull @PositiveOrZero Integer step,

        @NotNull
        @DecimalMin("0.000")                  // 음수 방지
        @Digits(integer = 4, fraction = 3)    // DECIMAL(7,3) => 정수부 4자리, 소수부 3자리
        BigDecimal distanceKm,

        @NotNull @PositiveOrZero Integer burnCalories,

        @PastOrPresent
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt
) {}
