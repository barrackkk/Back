package com.fitpet.server.report.application.mapper;

import com.fitpet.server.dailywalk.domain.entity.DailyWalk;
import com.fitpet.server.meal.domain.entity.Meal;
import com.fitpet.server.meal.presentation.dto.response.MealDetailInfo;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.ActivityRangeResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReportMapper {

    @Mapping(source = "step", target = "steps")
    @Mapping(source = "createdAt", target = "date", qualifiedByName = "toLocalDate")
    ActivityRangeResponse toActivityRangeResponse(DailyWalk dailyWalk);

    @Mapping(source = "id", target = "mealId")
    @Mapping(target = "imageUrl", ignore = true)
    MealDetailInfo toMealDetailInfo(Meal meal);

    @Named("toLocalDate")
    default LocalDate toLocalDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toLocalDate();
    }
}