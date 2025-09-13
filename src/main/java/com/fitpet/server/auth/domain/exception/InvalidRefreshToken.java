package com.fitpet.server.auth.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class InvalidRefreshToken extends BusinessException {
    public InvalidRefreshToken() {
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}
