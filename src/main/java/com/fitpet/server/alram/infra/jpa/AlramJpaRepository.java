package com.fitpet.server.alram.infra.jpa;

import com.fitpet.server.alram.domain.entity.AlramMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlramJpaRepository extends JpaRepository<AlramMessage, Long> {
}