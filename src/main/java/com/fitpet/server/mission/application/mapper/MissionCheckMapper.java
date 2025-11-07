package com.fitpet.server.mission.application.mapper;

import com.fitpet.server.mission.domain.entity.MissionCheck;
import com.fitpet.server.mission.presentation.dto.MissionCheckDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MissionCheckMapper {

    @Mapping(target = "missionCheckId", source = "id")
    @Mapping(target = "missionId", source = "mission.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "checkDate", source = "checkAt")
    MissionCheckDto toDto(MissionCheck missionCheck);

    List<MissionCheckDto> toDtos(List<MissionCheck> missionChecks);
}
