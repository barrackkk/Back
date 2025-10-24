package com.fitpet.server.meal.application.service;


import com.fitpet.server.meal.presentation.dto.MealDto.CreateRequest;
import com.fitpet.server.meal.presentation.dto.MealDto.CreateResponse;
import com.fitpet.server.meal.presentation.dto.MealDto.MealResponse;
import com.fitpet.server.meal.presentation.dto.MealDto.UpdateRequest;
import com.fitpet.server.meal.presentation.dto.MealDto.UpdateResponse;
import java.time.LocalDate;
import java.util.List;

public interface MealService {
    CreateResponse createMeal(Long userId, CreateRequest request);

    UpdateResponse updateMeal(Long userId, Long mealId, UpdateRequest request);

    List<MealResponse> getMealsByDate(Long userId, LocalDate date);

    void deleteMeal(Long userId, Long mealId);
}