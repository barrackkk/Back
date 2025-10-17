package com.fitpet.server.report.application.mapper;

import com.fitpet.server.dailywalk.domain.entity.DailyWalk;
import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.DailyWalkSummaryResponse;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.TodayWorkoutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReportMapper {
    TodayWorkoutResponse toTodayWorkoutResponse(GpsSession gpsSession);

    DailyWalkSummaryResponse toDailyWalkSummaryResponse(DailyWalk dailyWalk);
}