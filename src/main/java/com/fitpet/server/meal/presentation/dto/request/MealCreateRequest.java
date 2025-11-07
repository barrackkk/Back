package com.fitpet.server.meal.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class MealCreateRequest {
    @NotNull(message = "날짜는 null일 수 없습니다.")
    @PastOrPresent(message = "날짜는 오늘 또는 과거여야 합니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate day;

    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    private String title;

    @NotNull(message = "칼로리는 null일 수 없습니다.")
    @PositiveOrZero(message = "칼로리는 0 이상이어야 합니다.")
    private Integer kcal;

    @NotNull(message = "순서는 null일 수 없습니다.")
    @Positive(message = "순서는 1 이상이어야 합니다.")  // 아침1 점심2 저녁3
    private Integer sequence;
}