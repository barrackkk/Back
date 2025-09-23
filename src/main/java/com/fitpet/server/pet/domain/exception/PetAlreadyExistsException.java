package com.fitpet.server.pet.domain.exception;

import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;

public class PetAlreadyExistsException extends BusinessException {

    public PetAlreadyExistsException() {
        super(ErrorCode.PET_ALREADY_EXISTS);
    }
}
