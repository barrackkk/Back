package com.fitpet.server.domain.dailywalk.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailyWalkDto {
    private Long id;
    private Integer step;
    private Double distanceKm;
    private Integer burnCalories;
    private String createdAt;
    private String updatedAt;
}
