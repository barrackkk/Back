package com.fitpet.server.domain.dailywalk.exception;

public class DailyWalkNotFoundException extends RuntimeException {
    public DailyWalkNotFoundException(String message) {
        super(message);
    }
}
