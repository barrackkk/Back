package com.fitpet.server.report.presentation.dto.response;

import com.fitpet.server.meal.presentation.dto.response.MealDetailInfo;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DailyMealSummaryResponse {
    private LocalDate date;
    private Integer totalKcal;
    private List<MealDetailInfo> mealList;
}