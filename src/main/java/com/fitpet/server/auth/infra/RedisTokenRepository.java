package com.fitpet.server.auth.infra;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepository {
    private final StringRedisTemplate redisTemplate;
    private static final String REFRESH_PREFIX = "refresh:";

    public void save(Long userId, String refreshToken, long expirationMs) {
        redisTemplate.opsForValue()
                .set(REFRESH_PREFIX + userId, refreshToken, expirationMs, TimeUnit.MILLISECONDS);
    }

    public String find(Long userId) {
        return redisTemplate.opsForValue().get(REFRESH_PREFIX + userId);
    }

    public void delete(Long userId) {
        redisTemplate.delete(REFRESH_PREFIX + userId);
    }
}