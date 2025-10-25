package com.fitpet.server.meal.application.mapper;

import com.fitpet.server.meal.application.service.S3Service;
import com.fitpet.server.meal.domain.entity.Meal;
import com.fitpet.server.meal.presentation.dto.request.MealCreateRequest;
import com.fitpet.server.meal.presentation.dto.response.MealCreateResponse;
import com.fitpet.server.meal.presentation.dto.response.MealDetailResponse;
import com.fitpet.server.meal.presentation.dto.response.MealUpdateResponse;
import com.fitpet.server.user.domain.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MealMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "day", source = "request.day")
    @Mapping(target = "imageUrl", ignore = true)
    Meal toEntity(MealCreateRequest request, User user);

    @Mapping(source = "meal.id", target = "mealId")
    @Mapping(source = "meal.imageUrl", target = "imageUrl")
    @Mapping(source = "uploadUrl", target = "uploadUrl")
    MealCreateResponse toCreateResponse(Meal meal, String uploadUrl);

    @Mapping(source = "id", target = "mealId")
    @Mapping(source = "imageUrl", target = "imageUrl", qualifiedByName = "generateGetUrl")
    MealDetailResponse toMealResponse(Meal meal, @Context S3Service s3Service);

    @Mapping(source = "newImageKey", target = "imageUrl")
    @Mapping(source = "uploadUrl", target = "uploadUrl")
    MealUpdateResponse toUpdateResponse(String newImageKey, String uploadUrl);

    @Named("generateGetUrl")
    default String generateGetUrl(String objectKey, @Context S3Service s3Service) {
        if (s3Service == null) {
            return null;
        }
        return s3Service.generatePresignedGetUrl(objectKey);
    }
}