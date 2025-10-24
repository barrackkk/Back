package com.fitpet.server.meal.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MealUpdateRequest {
    @NotBlank
    private String title;
    @NotNull
    private Integer kcal;
    @NotNull
    private Integer sequence;
    private Boolean changeImage = false; // 기본값 false 설정
}