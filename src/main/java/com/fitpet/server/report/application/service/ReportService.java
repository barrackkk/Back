package com.fitpet.server.report.application.service;

import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.DailyWalkSummaryResponse;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.TodayWorkoutResponse;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<TodayWorkoutResponse> getTodayWorkouts(Long userId, LocalDate date);

    List<DailyWalkSummaryResponse> getWeeklyWalks(Long userId);
}