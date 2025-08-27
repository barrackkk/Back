package com.fitpet.server.domain.user.mapper;

import com.fitpet.server.domain.user.dto.UserCreateRequest;
import com.fitpet.server.domain.user.dto.UserDto;
import com.fitpet.server.domain.user.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateRequest request);

    UserDto toDto(User user);

}
