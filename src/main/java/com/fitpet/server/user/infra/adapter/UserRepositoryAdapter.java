package com.fitpet.server.user.infra.adapter;

import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.repository.UserRepository;
import com.fitpet.server.user.infra.jpa.UserJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpa;

    @Override
    public User save(User user) {
        return jpa.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpa.findById(id);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return jpa.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return jpa.existsByEmailAndIdNot(email, id);
    }

    @Override
    public boolean existsByNicknameAndIdNot(String nickname, Long id) {
        return jpa.existsByNicknameAndIdNot(nickname, id);
    }

    @Override
    public void delete(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("삭제할 사용자가 저장되지 않았습니다.");
        }
        jpa.deleteById(user.getId());
    }


}