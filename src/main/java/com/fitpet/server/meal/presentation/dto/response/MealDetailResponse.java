package com.fitpet.server.meal.presentation.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class MealDetailResponse {
    private Long mealId;
    private String title;
    @Setter
    private String imageUrl;
    private Integer kcal;
    private Integer sequence;
    private LocalDate day;
}