package com.fitpet.server.meal.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MealUpdateResponse {
    private String imageUrl;
    private String uploadUrl;
}