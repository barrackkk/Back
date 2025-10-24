package com.fitpet.server.meal.application.mapper;

import com.fitpet.server.meal.domain.entity.Meal;
import com.fitpet.server.meal.presentation.dto.MealDto.CreateRequest;
import com.fitpet.server.meal.presentation.dto.MealDto.MealResponse;
import com.fitpet.server.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MealMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", source = "request.date")
    Meal toEntity(CreateRequest request, User user);

    @Mapping(source = "id", target = "mealId")
    MealResponse toMealResponse(Meal meal);
}