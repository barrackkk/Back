package com.fitpet.server.user.presentation.dto;

import java.util.List;

public record GenderRankingResponse(
        List<UserRankingDto> top10
) {
}
