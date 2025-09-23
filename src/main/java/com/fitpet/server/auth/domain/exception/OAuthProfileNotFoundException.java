package com.fitpet.server.auth.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class OAuthProfileNotFoundException extends BusinessException {
    public OAuthProfileNotFoundException() {
        super(ErrorCode.OAUTH_PROFILE_NOT_FOUND);
    }
}