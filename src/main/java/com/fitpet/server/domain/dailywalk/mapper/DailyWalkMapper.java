package com.fitpet.server.domain.dailywalk.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fitpet.server.domain.dailywalk.dto.request.DailyWalkCreateRequest;
import com.fitpet.server.domain.dailywalk.dto.response.DailyWalkResponse;
import com.fitpet.server.domain.dailywalk.entity.DailyWalk;
import com.fitpet.server.domain.user.entity.User;

@Component
public class DailyWalkMapper {

    /** Request + User ref + createdAt 을 받아 엔티티 생성 */
    public DailyWalk toEntity(DailyWalkCreateRequest req, User userRef, LocalDateTime createdAt) {
        return DailyWalk.builder()
                .user(userRef)
                .step(req.step())
                .distanceKm(req.distanceKm())
                .burnCalories(req.burnCalories())
                .createdAt(createdAt)
                .build();
    }

    public DailyWalkResponse toResponse(DailyWalk e) {
        return DailyWalkResponse.from(e);
    }
}
