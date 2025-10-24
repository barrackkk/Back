package com.fitpet.server.meal.domain.repository;

import com.fitpet.server.meal.domain.entity.Meal;
import com.fitpet.server.user.domain.entity.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealRepository {
    Meal save(Meal meal);

    Optional<Meal> findById(Long id);

    void delete(Meal meal);

    List<Meal> findByUserAndDate(User user, LocalDate date);
}