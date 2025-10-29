package com.fitpet.server.report.application.service;

import com.fitpet.server.meal.presentation.dto.response.MealDetailResponse;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.ActivityRangeResponse;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.TodayActivityResponse;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    TodayActivityResponse getTodayActivity(Long userId, LocalDate date);

    List<ActivityRangeResponse> getActivityRange(Long userId, LocalDate from, LocalDate to);

    List<MealDetailResponse> getTodayMeals(Long userId, LocalDate date);

}