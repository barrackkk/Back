package com.fitpet.server.dailywalk.application.service;

import com.fitpet.server.dailywalk.presentation.dto.request.DailyWalkCreateRequest;
import com.fitpet.server.dailywalk.presentation.dto.request.DailyWalkStepUpdateRequest;
import com.fitpet.server.dailywalk.presentation.dto.response.DailyWalkResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface DailyWalkService {
    List<DailyWalkResponse> getAllByUserId(@NotNull Long userId);

    DailyWalkResponse getDailyWalkByUserIdAndDate(
            @NotNull Long userId,
            @NotNull @PastOrPresent LocalDate date
    );

    DailyWalkResponse createDailyWalk(@Valid DailyWalkCreateRequest req);

    void deleteDailyWalk(@NotNull Long dailyWalkId);

    void updateDailyWalkStep(
            @NotNull Long userId,
            @Valid DailyWalkStepUpdateRequest req
    );
}
