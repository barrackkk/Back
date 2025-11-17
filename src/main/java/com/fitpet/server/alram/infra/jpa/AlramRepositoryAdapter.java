package com.fitpet.server.alram.infra.jpa;

import com.fitpet.server.alram.domain.entity.AlramMessage;
import com.fitpet.server.alram.domain.repository.AlramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlramRepositoryAdapter implements AlramRepository {

    private final AlramJpaRepository alramJpaRepository;

    @Override
    public AlramMessage save(AlramMessage alramMessage) {
        return alramJpaRepository.save(alramMessage);
    }
}