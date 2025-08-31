package com.fitpet.server.domain.user.service;

import com.fitpet.server.domain.user.dto.UserCreateRequest;
import com.fitpet.server.domain.user.dto.UserDto;
import com.fitpet.server.domain.user.dto.UserUpdateRequest;

public interface UserService {
    UserDto createUser(UserCreateRequest request);

    UserDto findUser(Long userId);

    UserDto updateUser(Long userId, UserUpdateRequest req);
}
