package com.fitpet.server.domain.dailywalk.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

public record DailyWalkStepUpdateRequest(
        @NotNull @PastOrPresent
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull @PositiveOrZero
        Integer step
) {}
