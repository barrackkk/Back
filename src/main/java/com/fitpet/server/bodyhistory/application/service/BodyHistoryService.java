package com.fitpet.server.bodyhistory.application.service;

import com.fitpet.server.bodyhistory.presentation.dto.request.BodyHistoryCreateRequest;
import com.fitpet.server.bodyhistory.presentation.dto.request.BodyHistoryUpdateRequest;
import com.fitpet.server.bodyhistory.presentation.dto.response.BodyHistoryResponse;
import java.util.List;

public interface BodyHistoryService {

    BodyHistoryResponse createBodyHistory(BodyHistoryCreateRequest request);

    BodyHistoryResponse findBodyHistoryById(Long historyId);

    List<BodyHistoryResponse> findAllBodyHistoriesByUserId(Long userId);

    List<BodyHistoryResponse> findMonthlyBodyHistories(Long userId, int year, int month);

    BodyHistoryResponse updateBodyHistory(Long historyId, BodyHistoryUpdateRequest request);

    void deleteBodyHistory(Long historyId);
}