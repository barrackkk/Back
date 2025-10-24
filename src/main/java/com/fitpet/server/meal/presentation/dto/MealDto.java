package com.fitpet.server.meal.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

public class MealDto {

    @Getter
    public static class CreateRequest {
        @NotNull
        @PastOrPresent
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate date;
        @NotBlank
        private String title;
        @NotNull
        private Integer kcal;
        @NotNull
        private Integer sequence;
    }

    @Getter
    @Builder
    public static class CreateResponse {
        private Long mealId;
        private String imageUrl;
        private String uploadUrl;
    }

    @Getter
    public static class UpdateRequest {
        @NotBlank
        private String title;
        @NotNull
        private Integer kcal;
        @NotNull
        private Integer sequence;
        private Boolean changeImage = false;
    }

    @Getter
    @Builder
    public static class UpdateResponse {
        private String imageUrl;
        private String uploadUrl;
    }

    @Getter
    @Builder
    public static class MealResponse {
        private Long mealId;
        private String title;
        @Setter
        private String imageUrl;
        private Integer kcal;
        private Integer sequence;
        private LocalDate date;
    }
}