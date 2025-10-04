package com.fitpet.server.dailyworkout.application.service;

import com.fitpet.server.dailyworkout.application.mapper.GpsMapper;
import com.fitpet.server.dailyworkout.domain.entity.GpsLog;
import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import com.fitpet.server.dailyworkout.domain.repository.GpsLogRepository;
import com.fitpet.server.dailyworkout.domain.repository.GpsSessionRepository;
import com.fitpet.server.dailyworkout.presentation.dto.request.GpsLogRequest;
import com.fitpet.server.dailyworkout.presentation.dto.request.SessionEndRequest;
import com.fitpet.server.dailyworkout.presentation.dto.request.SessionStartRequest;
import com.fitpet.server.dailyworkout.presentation.dto.response.GpsLogResponse;
import com.fitpet.server.dailyworkout.presentation.dto.response.GpsSessionStartResponse;
import com.fitpet.server.dailyworkout.presentation.dto.response.SessionEndResponse;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GpsSessionServiceImpl implements GpsSessionService {

    private final GpsSessionRepository gpsSessionRepository;
    private final GpsLogRepository gpsLogRepository;
    private final UserRepository userRepository;
    private final GpsMapper gpsMapper;

    @Override
    public GpsSessionStartResponse startSession(SessionStartRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. userId: " + request.getUserId()));

        // (수정) @Builder 어노테이션에 맞춰 빌더 패턴으로 객체 생성
        GpsSession newSession = GpsSession.builder()
                .user(user)
                .startTime(request.getStartTime())
                .build();

        GpsSession savedSession = gpsSessionRepository.save(newSession);

        return gpsMapper.toSessionStartResponse(savedSession);
    }

    @Override
    public GpsLogResponse logGps(GpsLogRequest request) {
        GpsSession session = gpsSessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new EntityNotFoundException("세션을 찾을 수 없습니다. sessionId: " + request.getSessionId()));

        GpsLog newLog = gpsMapper.toGpsLogEntity(request, session);
        GpsLog savedLog = gpsLogRepository.save(newLog);

        return gpsMapper.toGpsLogResponse(savedLog);
    }

    @Override
    public SessionEndResponse endSession(SessionEndRequest request) {
        GpsSession session = gpsSessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new EntityNotFoundException("세션을 찾을 수 없습니다. sessionId: " + request.getSessionId()));

        gpsMapper.updateSessionFromEndRequest(request, session);
        session.setEndTime(request.getEndTime());

        return gpsMapper.toSessionEndResponse(session);
    }

}