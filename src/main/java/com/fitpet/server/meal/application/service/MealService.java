package com.fitpet.server.meal.application.service;

import com.fitpet.server.meal.presentation.dto.request.MealCreateRequest;
import com.fitpet.server.meal.presentation.dto.request.MealUpdateRequest;
import com.fitpet.server.meal.presentation.dto.response.MealCreateResponse;
import com.fitpet.server.meal.presentation.dto.response.MealDetailResponse;
import java.time.LocalDate;
import java.util.List;

public interface MealService {
    MealCreateResponse createMeal(Long userId, MealCreateRequest request);

    Object updateMeal(Long userId, Long mealId, MealUpdateRequest request);

    List<MealDetailResponse> getMealsByDate(Long userId, LocalDate day);

    void deleteMeal(Long userId, Long mealId);
}