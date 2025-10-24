package com.fitpet.server.meal.application.service;


import com.fitpet.server.meal.presetation.dto.MealDto.CreateRequest;
import com.fitpet.server.meal.presetation.dto.MealDto.CreateResponse;
import com.fitpet.server.meal.presetation.dto.MealDto.UpdateRequest;
import com.fitpet.server.meal.presetation.dto.MealDto.UpdateResponse;

public interface MealService {
    CreateResponse createMeal(Long userId, CreateRequest request);

    UpdateResponse updateMeal(Long userId, Long mealId, UpdateRequest request);

    void deleteMeal(Long userId, Long mealId);
}