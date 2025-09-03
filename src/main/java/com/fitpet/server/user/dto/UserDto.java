package com.fitpet.server.user.dto;

import com.fitpet.server.user.entity.Gender;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserDto(
    Long userId,
    String email,
    String nickname,
    Integer age,
    Gender gender,
    Double weightKg,
    Double targetWeightKg,
    Double heightCm,
    Double pbf,
    Double targetPbf,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}