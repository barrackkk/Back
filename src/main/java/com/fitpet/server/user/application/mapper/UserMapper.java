package com.fitpet.server.user.application.mapper;

import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.presentation.dto.UserCreateRequest;
import com.fitpet.server.user.presentation.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateRequest request);

    @Mapping(source = "id", target = "userId")
    UserDto toDto(User user);

}
