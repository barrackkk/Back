package com.fitpet.server.domain.user.repository;

import com.fitpet.server.domain.user.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean findByEmail(String email);

    boolean findByNickName(String nickName);
}
