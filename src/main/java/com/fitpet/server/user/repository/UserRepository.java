package com.fitpet.server.user.repository;

import com.fitpet.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickName);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByNicknameAndIdNot(String nickname, Long id);
}
