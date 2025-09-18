package com.fitpet.server.auth.domain.exception;


import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class InvalidAccessTokenException extends BusinessException {
    public InvalidAccessTokenException() {
        super(ErrorCode.INVALID_ACCESS_TOKEN);
    }
}