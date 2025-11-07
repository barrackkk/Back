package com.fitpet.server.alram.domain.repository;

import com.fitpet.server.alram.domain.entity.AlramMessage;

public interface AlramRepository {
    AlramMessage save(AlramMessage alramMessage);
}