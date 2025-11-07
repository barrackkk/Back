package com.fitpet.server.meal.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MealCreateResponse {
    private Long mealId;
    private String imageUrl; // S3 Key
    private String uploadUrl;
}