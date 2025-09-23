package com.fitpet.server.auth.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class OAuthInvalidTokenException extends BusinessException {
    public OAuthInvalidTokenException() {
        super(ErrorCode.OAUTH_INVALID_TOKEN);
    }
}
