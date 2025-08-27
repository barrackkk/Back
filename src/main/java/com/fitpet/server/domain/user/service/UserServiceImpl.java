package com.fitpet.server.domain.user.service;

import com.fitpet.server.domain.user.dto.UserCreateRequest;
import com.fitpet.server.domain.user.dto.UserDto;
import com.fitpet.server.domain.user.entity.User;
import com.fitpet.server.domain.user.mapper.UserMapper;
import com.fitpet.server.domain.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private static UserRepository userRepository;
    private static UserMapper userMapper;

    @Override
    public UserDto createUser(UserCreateRequest request) throws IllegalAccessException {
        log.debug("[UserService]: 사용자 등록 요청 - UserCreateRequest: {}", request);

        validateUserCreateRequest(request);

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);

        log.info("[UserService]: 사용자 등록 완료: id={}, nickname={}", savedUser.getId(), savedUser.getNickName());

        return userMapper.toDto(savedUser);

    }

    public void validateUserCreateRequest(UserCreateRequest request) throws IllegalAccessException {
        if (userRepository.findByEmail(request.email())) {
            throw new IllegalAccessException("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickName(request.nickname())) {
            throw new IllegalAccessException("이미 존재하는 닉네임입니다.");
        }

    }


}
