package com.fitpet.server.shared.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    DAILY_WALK_NOT_FOUND(HttpStatus.NOT_FOUND, "D001", "해당 걸음 기록을 찾을 수 없습니다."),
    DAILY_WALK_ALREADY_EXISTS(HttpStatus.CONFLICT, "D002", "해당 날짜의 걸음 기록이 이미 존재합니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "C001", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C999", "서버 에러가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
