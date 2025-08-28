package com.fitpet.server.domain.dailywalk.exception;

import com.fitpet.server.global.exception.BusinessException;
import com.fitpet.server.global.exception.ErrorCode;

public class DailyWalkNotFoundException extends BusinessException {
    public DailyWalkNotFoundException() {
        super(ErrorCode.DAILY_WALK_NOT_FOUND);
    }
}
