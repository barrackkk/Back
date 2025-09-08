package com.fitpet.server.dailywalk.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyWalkStepUpdateRequest(
        @NotNull @PastOrPresent
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull @PositiveOrZero
        Integer step,

        @NotNull @PositiveOrZero
        BigDecimal distanceKm,

        @NotNull @PositiveOrZero
        Integer burnCalories
) {
}
