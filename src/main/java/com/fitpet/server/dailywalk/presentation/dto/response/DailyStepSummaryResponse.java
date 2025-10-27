package com.fitpet.server.dailywalk.presentation.dto.response;

import java.time.LocalDate;

public record DailyStepSummaryResponse(
        LocalDate date,
        int step
) {
}