package com.fitpet.server.dailyworkout.application.mapper;

import com.fitpet.server.dailyworkout.domain.entity.GpsLog;
import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import com.fitpet.server.dailyworkout.presentation.dto.response.GpsLogResponse;
import com.fitpet.server.dailyworkout.presentation.dto.response.GpsSessionResponse;
import com.fitpet.server.dailyworkout.presentation.dto.response.GpsSessionStartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GpsMapper {

    @Mapping(source = "id", target = "sessionId")
    @Mapping(source = "gpsLogs", target = "path")
    GpsSessionResponse toGpsSessionResponse(GpsSession gpsSession);

    GpsLogResponse toGpsLogResponse(GpsLog gpsLog);

    @Mapping(source = "id", target = "sessionId")
    GpsSessionStartResponse toGpsSessionStartResponse(GpsSession gpsSession);
}