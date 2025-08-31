package com.fitpet.server.domain.user.service;

import com.fitpet.server.domain.user.dto.UserCreateRequest;
import com.fitpet.server.domain.user.dto.UserDto;

public interface UserService {
    UserDto createUser(UserCreateRequest request);

    UserDto findUser(Long userId);
}
