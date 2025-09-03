package com.fitpet.server.user.mapper;

import com.fitpet.server.user.dto.UserCreateRequest;
import com.fitpet.server.user.dto.UserDto;
import com.fitpet.server.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateRequest request);

    @Mapping(source = "id", target = "userId")
    UserDto toDto(User user);

}
