package com.fitpet.server.meal.application.service;

import com.fitpet.server.meal.application.mapper.MealMapper;
import com.fitpet.server.meal.domain.entity.Meal;
import com.fitpet.server.meal.domain.repository.MealRepository;
import com.fitpet.server.meal.presetation.dto.MealDto.CreateRequest;
import com.fitpet.server.meal.presetation.dto.MealDto.CreateResponse;
import com.fitpet.server.meal.presetation.dto.MealDto.UpdateRequest;
import com.fitpet.server.meal.presetation.dto.MealDto.UpdateResponse;
import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final MealMapper mealMapper;
    private final S3Service s3Service;

    @Override
    public CreateResponse createMeal(Long userId, CreateRequest request) {
        User user = findUserById(userId);
        Meal meal = mealMapper.toEntity(request, user);

        String imageKey = s3Service.createImageKey(userId);
        meal.setImageUrl(imageKey);
        Meal savedMeal = mealRepository.save(meal);

        String uploadUrl = s3Service.generatePresignedPutUrl(imageKey);

        return CreateResponse.builder()
                .mealId(savedMeal.getId())
                .imageUrl(imageKey)
                .uploadUrl(uploadUrl)
                .build();
    }

    @Override
    public UpdateResponse updateMeal(Long userId, Long mealId, UpdateRequest request) {
        User user = findUserById(userId);
        Meal meal = findMealById(mealId);
        authorizeMealOwner(user, meal);

        meal.setTitle(request.getTitle());
        meal.setKcal(request.getKcal());
        meal.setSequence(request.getSequence());

        if (request.getChangeImage()) {
            if (meal.getImageUrl() != null) {
                s3Service.deleteObject(meal.getImageUrl());
            }
            String newImageKey = s3Service.createImageKey(userId);
            meal.setImageUrl(newImageKey);
            String uploadUrl = s3Service.generatePresignedPutUrl(newImageKey);

            return UpdateResponse.builder()
                    .imageUrl(newImageKey)
                    .uploadUrl(uploadUrl)
                    .build();
        }
        return null;
    }

    @Override
    public void deleteMeal(Long userId, Long mealId) {
        User user = findUserById(userId);
        Meal meal = findMealById(mealId);
        authorizeMealOwner(user, meal);

        if (meal.getImageUrl() != null) {
            s3Service.deleteObject(meal.getImageUrl());
        }
        mealRepository.delete(meal);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Meal findMealById(Long mealId) {
        return mealRepository.findById(mealId)
                .orElseThrow(() -> new EntityNotFoundException("식단을 찾을 수 없습니다."));
    }

    private void authorizeMealOwner(User user, Meal meal) {
        if (!meal.getUser().getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.PET_ACCESS_DENIED);
        }
    }
}