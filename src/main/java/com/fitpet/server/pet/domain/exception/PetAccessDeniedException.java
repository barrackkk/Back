package com.fitpet.server.pet.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class PetAccessDeniedException extends BusinessException {

    public PetAccessDeniedException() {
        super(ErrorCode.PET_ACCESS_DENIED);
    }
}
