package com.fitpet.server.dailywalk.application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.fitpet.server.dailywalk.presentation.dto.request.DailyWalkCreateRequest;
import com.fitpet.server.dailywalk.domain.entity.DailyWalk;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
public interface DailyWalkService {
    List<DailyWalk> getAllByUserId(@NotNull Long userId);

    DailyWalk getDailyWalkByUserIdAndDate(
            @NotNull Long userId,
            @NotNull @PastOrPresent LocalDate date
    );

    DailyWalk createDailyWalk(@Valid DailyWalkCreateRequest req);

    void deleteDailyWalk(@NotNull Long dailyWalkId);

    void updateDailyWalkStep(
            @NotNull Long userId,
            @NotNull @PastOrPresent LocalDate date,
            @NotNull @PositiveOrZero Integer newStep
    );
}
