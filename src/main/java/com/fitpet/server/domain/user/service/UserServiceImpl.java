package com.fitpet.server.domain.user.service;

import com.fitpet.server.domain.user.dto.UserCreateRequest;
import com.fitpet.server.domain.user.dto.UserDto;
import com.fitpet.server.domain.user.entity.User;
import com.fitpet.server.domain.user.mapper.UserMapper;
import com.fitpet.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDto createUser(UserCreateRequest request) {
        log.debug("[UserService]: 사용자 등록 요청 - UserCreateRequest: {}", request);

        validateUserCreateRequest(request);

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);

        log.info("[UserService]: 사용자 등록 완료: id={}, nickname={}", savedUser.getId(), savedUser.getNickname());

        return userMapper.toDto(savedUser);

    }

    @Override
    public UserDto findUser(Long userId) {
        log.debug("[UserService]: 사용자 조회 요청: id={}", userId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> {
                log.warn("[UserService]: 사용자 조회 요청 실패: id={}", userId);
                throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
            });

        return userMapper.toDto(user);
    }

    public void validateUserCreateRequest(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (userRepository.existsByNickname(request.nickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

    }


}
