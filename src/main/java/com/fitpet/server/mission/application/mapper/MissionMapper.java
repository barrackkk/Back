package com.fitpet.server.mission.application.mapper;

import com.fitpet.server.mission.domain.entity.Mission;
import com.fitpet.server.mission.presentation.dto.MissionCreateRequest;
import com.fitpet.server.mission.presentation.dto.MissionDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MissionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Mission toEntity(MissionCreateRequest request);

    @Mapping(target = "missionId", source = "id")
    MissionDto toDto(Mission mission);

    List<MissionDto> toDtos(List<Mission> missions);
}
