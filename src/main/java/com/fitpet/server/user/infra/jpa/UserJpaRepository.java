package com.fitpet.server.user.infra.jpa;

import com.fitpet.server.user.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickName);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByNicknameAndIdNot(String nickname, Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByProviderAndProviderUid(String provider, String providerUid);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User user set user.dailyStepCount = 0")
    int resetDailyStepCount();
}
