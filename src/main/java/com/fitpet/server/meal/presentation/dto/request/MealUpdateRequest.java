package com.fitpet.server.meal.presentation.dto.request;

import lombok.Getter;

@Getter
public class MealUpdateRequest {
    private String title;
    private Integer kcal;
    private Integer sequence;
    private Boolean changeImage;
}