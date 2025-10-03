package com.fitpet.server.dailyworkout.application.mapper;

import com.fitpet.server.dailyworkout.domain.entity.GpsLog;
import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import com.fitpet.server.dailyworkout.presentation.dto.request.GpsLogRequest;
import com.fitpet.server.dailyworkout.presentation.dto.request.SessionEndRequest;
import com.fitpet.server.dailyworkout.presentation.dto.response.GpsLogResponse;
import com.fitpet.server.dailyworkout.presentation.dto.response.GpsSessionStartResponse;
import com.fitpet.server.dailyworkout.presentation.dto.response.SessionEndResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE // 3. 설정 추가
)
public interface GpsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gpsSession", source = "session")
    GpsLog toGpsLogEntity(GpsLogRequest request, GpsSession session);
    
    @Mapping(source = "id", target = "sessionId")
    GpsSessionStartResponse toGpsSessionStartResponse(GpsSession session);

    @Mapping(source = "id", target = "logId")
    GpsLogResponse toGpsLogResponse(GpsLog log);

    @Mapping(source = "id", target = "sessionId")
    SessionEndResponse toSessionEndResponse(GpsSession session);

    @Mapping(source = "distance", target = "totalDistance")
    void updateSessionFromEndRequest(SessionEndRequest dto, @MappingTarget GpsSession session);
}