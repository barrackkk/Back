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
        meal.setImageUrl(imageKey); // 생성된 이미지 키 저장
        Meal savedMeal = mealRepository.save(meal);

        String uploadUrl = s3Service.generatePresignedPutUrl(imageKey);

        return CreateResponse.builder()
                .mealId(savedMeal.getId())
                .imageUrl(imageKey) // S3 키 반환
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
            // 기존 이미지 삭제 (선택적)
            if (meal.getImageUrl() != null) {
                s3Service.deleteObject(meal.getImageUrl());
            }
            // 새 이미지 키 생성 및 Pre-signed URL 발급
            String newImageKey = s3Service.createImageKey(userId);
            meal.setImageUrl(newImageKey);
            String uploadUrl = s3Service.generatePresignedPutUrl(newImageKey);

            return UpdateResponse.builder()
                    .imageUrl(newImageKey)
                    .uploadUrl(uploadUrl)
                    .build();
        }
        return null; // 이미지 변경 없으면 null 반환 (또는 다른 응답 설계)
    }

    @Override
    public void deleteMeal(Long userId, Long mealId) {
        User user = findUserById(userId);
        Meal meal = findMealById(mealId);
        authorizeMealOwner(user, meal);

        // S3 이미지 삭제
        if (meal.getImageUrl() != null) {
            s3Service.deleteObject(meal.getImageUrl());
        }
        // DB 데이터 삭제
        mealRepository.delete(meal);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Meal findMealById(Long mealId) {
        // Meal 관련 ErrorCode 추가 필요
        return mealRepository.findById(mealId)
                .orElseThrow(() -> new EntityNotFoundException("식단을 찾을 수 없습니다."));
    }

    private void authorizeMealOwner(User user, Meal meal) {
        // Meal 관련 ErrorCode 추가 필요
        if (!meal.getUser().getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.PET_ACCESS_DENIED); // 적절한 ErrorCode로 변경
        }
    }
}