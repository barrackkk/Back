package com.fitpet.server.domain.dailywalk.mapper;

import com.fitpet.server.domain.dailywalk.dto.request.DailyWalkCreateRequest;
import com.fitpet.server.domain.dailywalk.dto.response.DailyWalkResponse;
import com.fitpet.server.domain.dailywalk.entity.DailyWalk;
import com.fitpet.server.domain.user.entity.User;
import org.mapstruct.*;

import java.math.RoundingMode;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {RoundingMode.class, LocalDateTime.class})
public interface DailyWalkMapper {

    @Mapping(target = "user", source = "userRef")
    @Mapping(target = "step", source = "req.step")
    @Mapping(target = "distanceKm",
            expression = "java(req.distanceKm() != null ? req.distanceKm().setScale(3, RoundingMode.HALF_UP) : null)")
    @Mapping(target = "burnCalories", source = "req.burnCalories")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    DailyWalk toEntity(DailyWalkCreateRequest req, User userRef, LocalDateTime createdAt);

    DailyWalkResponse toResponse(DailyWalk e);
}
