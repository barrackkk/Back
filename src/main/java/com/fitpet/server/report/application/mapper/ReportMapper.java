package com.fitpet.server.report.application.mapper;

import com.fitpet.server.dailywalk.domain.entity.DailyWalk;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.ActivityRangeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReportMapper {
    ActivityRangeResponse toActivityRangeResponse(DailyWalk dailyWalk);
}