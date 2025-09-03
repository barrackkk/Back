package com.fitpet.server.user.application.service;

import com.fitpet.server.user.application.mapper.UserMapper;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.repository.UserRepository;
import com.fitpet.server.user.presentation.dto.UserCreateRequest;
import com.fitpet.server.user.presentation.dto.UserDto;
import com.fitpet.server.user.presentation.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserCreateRequest request) {
        log.debug("회원가입 요청 email={}, nickname={}", request.email(), request.nickname());

        validateUserCreateRequest(request);

        User user = userMapper.toEntity(request);
        user.changePassword(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        validateUserUpdateRequest(userId, request);

        if (StringUtils.hasText(request.password())) {
            user.changePassword(passwordEncoder.encode(request.password()));
        }

        user.update(request);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        userRepository.delete(user);
    }


    private void validateUserCreateRequest(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            log.warn("회원가입 실패 - 중복 이메일: {}", request.email());
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (userRepository.existsByNickname(request.nickname())) {
            log.warn("회원가입 실패 - 중복 닉네임: {}", request.nickname());
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }

    private void validateUserUpdateRequest(Long userId, UserUpdateRequest request) {
        if (request.email() != null &&
            userRepository.existsByEmailAndIdNot(request.email(), userId)) {
            log.warn("사용자 수정 실패 - 이메일 중복: {} (요청자 id: {})", request.email(), userId);
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (request.nickname() != null &&
            userRepository.existsByNicknameAndIdNot(request.nickname(), userId)) {
            log.warn("사용자 수정 실패 - 닉네임 중복: {} (요청자 id: {})", request.nickname(), userId);
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }
}