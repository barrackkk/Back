package com.fitpet.server.auth.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class InvalidLoginException extends BusinessException {
    public InvalidLoginException() {
        super(ErrorCode.INVALID_LOGIN);
    }
}
