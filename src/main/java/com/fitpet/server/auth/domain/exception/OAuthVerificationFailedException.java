package com.fitpet.server.auth.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class OAuthVerificationFailedException extends BusinessException {
    public OAuthVerificationFailedException() {
        super(ErrorCode.OAUTH_VERIFICATION_FAILED);
    }
}

