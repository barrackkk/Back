package com.fitpet.server.dailywalk.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class DailyWalkNotFoundException extends BusinessException {
    public DailyWalkNotFoundException() {
        super(ErrorCode.DAILY_WALK_NOT_FOUND);
    }
}
