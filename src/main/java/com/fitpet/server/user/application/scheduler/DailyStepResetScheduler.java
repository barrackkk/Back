package com.fitpet.server.user.application.scheduler;

import com.fitpet.server.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyStepResetScheduler {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void resetDailyStepCount() {
        int updated = userRepository.resetDailyStepCount();
        log.info("[DailyStepResetScheduler] 일일 걸음수 초기화 완료 - 업데이트된 사용자 수: {}", updated);
    }
}