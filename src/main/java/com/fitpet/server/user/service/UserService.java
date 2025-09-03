package com.fitpet.server.user.service;

import com.fitpet.server.user.dto.UserCreateRequest;
import com.fitpet.server.user.dto.UserDto;
import com.fitpet.server.user.dto.UserUpdateRequest;

public interface UserService {
    UserDto createUser(UserCreateRequest request);

    UserDto findUser(Long userId);

    UserDto updateUser(Long userId, UserUpdateRequest req);

    void deleteUser(Long userID);
}
