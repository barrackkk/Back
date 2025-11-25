package com.fitpet.server.user.presentation.dto;

public record UserRankingDto(
        Long userId,
        String nickname,
        Integer dailyStepCount
) {
}
