package com.fitpet.server.auth.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class InvalidLogin extends BusinessException {
    public InvalidLogin() {
        super(ErrorCode.INVALID_LOGIN);
    }
}
