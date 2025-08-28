package com.fitpet.server.domain.dailywalk.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.fitpet.server.domain.dailywalk.dto.request.DailyWalkCreateRequest;
import com.fitpet.server.domain.dailywalk.entity.DailyWalk;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
public interface DailyWalkService {

    List<DailyWalk> getDailyWalksByUser(@NotNull Long userId);

    DailyWalk getDailyWalkByUserAndCreatedAt(
            @NotNull Long userId,
            @NotNull @PastOrPresent LocalDateTime createdAt
    );

    DailyWalk createDailyWalk(@Valid DailyWalkCreateRequest req);

    void deleteDailyWalk(@NotNull Long dailyWalkId);

    void updateDailyWalkStep(
            @NotNull Long userId,
            @NotNull @PastOrPresent LocalDateTime createdAt,
            @NotNull @PositiveOrZero Integer newStep
    );
}
