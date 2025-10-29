package com.fitpet.server.meal.presentation.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MealCalendarResponse {
    private int year;
    private int month;
    private List<DayInfo> days;
}

