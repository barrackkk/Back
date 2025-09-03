package com.fitpet.server.dailywalk.application.mapper;

import com.fitpet.server.dailywalk.presentation.dto.request.DailyWalkCreateRequest;
import com.fitpet.server.dailywalk.presentation.dto.response.DailyWalkResponse;
import com.fitpet.server.dailywalk.domain.entity.DailyWalk;
import com.fitpet.server.user.entity.User;
import org.mapstruct.*;

import java.math.RoundingMode;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {RoundingMode.class, LocalDateTime.class})
public interface DailyWalkMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userRef")
    @Mapping(target = "step", source = "req.step")
    @Mapping(target = "distanceKm",
            expression = "java(req.distanceKm() != null ? req.distanceKm().setScale(3, RoundingMode.HALF_UP) : null)")
    @Mapping(target = "burnCalories", source = "req.burnCalories")
    DailyWalk toEntity(DailyWalkCreateRequest req, User userRef, LocalDateTime createdAt);
}
