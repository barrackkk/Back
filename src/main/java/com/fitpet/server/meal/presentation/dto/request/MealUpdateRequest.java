package com.fitpet.server.meal.presentation.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class MealUpdateRequest {
    private String title;

    @PositiveOrZero(message = "칼로리는 0 이상이어야 합니다.")
    private Integer kcal;

    @Positive(message = "순서는 1 이상이어야 합니다.")
    private Integer sequence;

    private Boolean changeImage = false;
}