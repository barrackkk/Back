package com.fitpet.server.dailywalk.presentation.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

public record DailyWalkStepUpdateRequest(
        @NotNull @PastOrPresent
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull @PositiveOrZero
        Integer step,

        @NotNull@PositiveOrZero
        BigDecimal distanceKm,

        @NotNull @PositiveOrZero
        Integer burnCalories
) {}
