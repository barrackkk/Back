package com.fitpet.server.domain.dailywalk.dto.request;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

public record DailyWalkStepUpdateRequest(
        @NotNull @PastOrPresent
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,

        @NotNull @PositiveOrZero
        Integer step
) {}
