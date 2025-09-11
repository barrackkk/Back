package com.fitpet.server.bodyhistory.application.service;

import com.fitpet.server.bodyhistory.application.mapper.BodyHistoryMapper;
import com.fitpet.server.bodyhistory.domain.entity.BodyHistory;
import com.fitpet.server.bodyhistory.domain.repository.BodyHistoryRepository;
import com.fitpet.server.bodyhistory.presentation.dto.request.BodyHistoryCreateRequest;
import com.fitpet.server.bodyhistory.presentation.dto.request.BodyHistoryUpdateRequest;
import com.fitpet.server.bodyhistory.presentation.dto.response.BodyHistoryResponse;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.repository.UserRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BodyHistoryServiceImpl implements BodyHistoryService {
    private final BodyHistoryRepository bodyHistoryRepository;
    private final UserRepository userRepository;
    private final BodyHistoryMapper bodyHistoryMapper;

    @Override
    public BodyHistoryResponse createBodyHistory(BodyHistoryCreateRequest req) {
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        BodyHistory bodyHistory = bodyHistoryMapper.toEntity(req, user);
        BodyHistory savedBodyHistory = bodyHistoryRepository.save(bodyHistory);

        return bodyHistoryMapper.toResponse(savedBodyHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public BodyHistoryResponse findBodyHistoryById(Long historyId) {
        return bodyHistoryRepository.findById(historyId)
                .map(bodyHistoryMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("기록을 찾지 못 했습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BodyHistoryResponse> findAllBodyHistoriesByUserId(Long userId) {
        return bodyHistoryRepository.findAllByUserId(userId).stream()
                .map(bodyHistoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BodyHistoryResponse> findMonthlyBodyHistories(Long userId, int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return bodyHistoryRepository.findAllByUserIdAndDate(userId, startDate, endDate)
                .stream()
                .map(bodyHistoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BodyHistoryResponse updateBodyHistory(Long historyId, BodyHistoryUpdateRequest req) {
        BodyHistory existingHistory = bodyHistoryRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("기록을 찾지 못 했습니다"));

        bodyHistoryMapper.updateBodyHistory(req, existingHistory);

        return bodyHistoryMapper.toResponse(existingHistory);
    }

    @Override
    public void deleteBodyHistory(Long historyId) {
        if (!bodyHistoryRepository.existsById(historyId)) {
            throw new RuntimeException("삭제할 기록을 찾지 못 했습니다.");
        }

        bodyHistoryRepository.deleteById(historyId);
    }
}
