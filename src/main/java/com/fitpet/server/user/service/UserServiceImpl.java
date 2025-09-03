package com.fitpet.server.user.service;

import com.fitpet.server.user.dto.UserCreateRequest;
import com.fitpet.server.user.dto.UserDto;
import com.fitpet.server.user.dto.UserUpdateRequest;
import com.fitpet.server.user.entity.User;
import com.fitpet.server.user.mapper.UserMapper;
import com.fitpet.server.user.repository.UserRepository;
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
        log.debug("[UserService] 회원가입 요청 email={}, nickname={}", request.email(), request.nickname());

        validateUserCreateRequest(request);

        User user = userMapper.toEntity(request);
        user.changePassword(passwordEncoder.encode(request.password()));
        User savedUser = userRepository.save(user);

        log.info("[UserService]: 사용자 등록 완료: id={}, nickname={}", user.getId(), user.getNickname());
        return userMapper.toDto(savedUser);

    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUser(Long userId) {
        log.debug("[UserService]: 사용자 조회 요청: id={}", userId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> {
                log.warn("[UserService]: 사용자 조회 요청 실패: id={}", userId);
                throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
            });

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long userId, UserUpdateRequest request) {

        log.debug("[UserService]: 사용자 수정 요청: id={}", userId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        validateUserUpdateRequest(userId, request);
        if (StringUtils.hasText(request.password())) {
            user.changePassword(passwordEncoder.encode(request.password()));
        }
        user.update(request);

        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {

        log.debug("[UserService]: 사용자 삭제 요청: id={}", userId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 사용자입니다."));

        userRepository.delete(user);
    }

    private void validateUserCreateRequest(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (userRepository.existsByNickname(request.nickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }

    private void validateUserUpdateRequest(Long userId, UserUpdateRequest request) {
        if (request.email() != null &&
            userRepository.existsByEmailAndIdNot(request.email(), userId)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (request.nickname() != null &&
            userRepository.existsByNicknameAndIdNot(request.nickname(), userId)) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }
}
