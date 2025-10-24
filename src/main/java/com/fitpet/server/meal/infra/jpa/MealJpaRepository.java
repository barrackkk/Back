package com.fitpet.server.meal.infra.jpa;

import com.fitpet.server.meal.domain.entity.Meal;
import com.fitpet.server.user.domain.entity.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealJpaRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserAndDate(User user, LocalDate date);
}