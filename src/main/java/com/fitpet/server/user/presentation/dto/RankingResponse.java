package com.fitpet.server.user.presentation.dto;

import java.util.List;

public record RankingResponse(
        List<UserRankingDto> top10,
        int myRank
) {
}
