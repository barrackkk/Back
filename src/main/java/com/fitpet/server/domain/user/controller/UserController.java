package com.fitpet.server.domain.user.controller;

import com.fitpet.server.domain.user.dto.UserCreateRequest;
import com.fitpet.server.domain.user.dto.UserDto;
import com.fitpet.server.domain.user.dto.UserUpdateRequest;
import com.fitpet.server.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(
        @Valid @RequestBody UserCreateRequest userCreateRequest
    ) {
        log.info("[UserController] 사용자 회원가입 요청: email: {}, nickname: {}",
            userCreateRequest.email(), userCreateRequest.nickname());

        UserDto createdUser = userService.createUser(userCreateRequest);

        log.info("[UserController] 사용자 회원가입 완료: id: {}, email: {}",
            createdUser.userId(), createdUser.email());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> find(
        @PathVariable Long userId
    ) {
        log.info("[UserController] 사용자 조회 요청: id: {}", userId);

        UserDto user = userService.findUser(userId);

        log.info("[UserController] 사용자 조회 완료: id: {}", userId);

        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> update(
        @PathVariable Long userId,
        @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        log.info("[UserController] 사용자 수정 요청: id: {}", userId);

        UserDto user = userService.updateUser(userId, userUpdateRequest);

        log.info("[UserController] 사용자 수정 완료: id: {}", userId);

        return ResponseEntity.status(HttpStatus.OK).body(user);

    }
}
