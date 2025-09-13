package com.fitpet.server.bodyhistory.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class BodyHistoryNotFoundException extends BusinessException {
    public BodyHistoryNotFoundException() {
        super(ErrorCode.BODY_HISTORY_NOT_FOUND);
    }
  
}
