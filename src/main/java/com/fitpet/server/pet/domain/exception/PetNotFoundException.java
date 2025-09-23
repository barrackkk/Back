package com.fitpet.server.pet.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class PetNotFoundException extends BusinessException {
    public PetNotFoundException() {
        super(ErrorCode.PET_NOT_FOUND);
    }
}
