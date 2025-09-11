package com.fitpet.server.bodyhistory.application.mapper;

import com.fitpet.server.bodyhistory.domain.entity.BodyHistory;
import com.fitpet.server.bodyhistory.presentation.dto.request.BodyHistoryCreateRequest;
import com.fitpet.server.bodyhistory.presentation.dto.request.BodyHistoryUpdateRequest;
import com.fitpet.server.bodyhistory.presentation.dto.response.BodyHistoryResponse;
import com.fitpet.server.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BodyHistoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "heightCm", source = "request.heightCm")
    @Mapping(target = "weightKg", source = "request.weightKg")
    @Mapping(target = "pbf", source = "request.pbf")
    BodyHistory toEntity(BodyHistoryCreateRequest request, User user);

    @Mapping(source = "user.id", target = "userId")
    BodyHistoryResponse toResponse(BodyHistory entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateBodyHistory(
            BodyHistoryUpdateRequest request,
            @MappingTarget BodyHistory entity
    );
}