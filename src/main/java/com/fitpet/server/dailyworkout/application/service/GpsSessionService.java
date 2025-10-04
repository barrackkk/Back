package com.fitpet.server.dailyworkout.application.service;

import com.fitpet.server.dailyworkout.presentation.dto.request.GpsLogRequest;
import com.fitpet.server.dailyworkout.presentation.dto.request.SessionEndRequest;
import com.fitpet.server.dailyworkout.presentation.dto.request.SessionStartRequest;
import com.fitpet.server.dailyworkout.presentation.dto.response.GpsLogResponse;
import com.fitpet.server.dailyworkout.presentation.dto.response.GpsSessionStartResponse;
import com.fitpet.server.dailyworkout.presentation.dto.response.SessionEndResponse;

public interface GpsSessionService {

    /**
     * GPS 운동 세션을 시작합니다.
     */
    GpsSessionStartResponse startSession(SessionStartRequest request);

    /**
     * GPS 로그를 기록합니다.
     */
    GpsLogResponse logGps(GpsLogRequest request);

    /**
     * GPS 운동 세션을 종료합니다.
     */
    SessionEndResponse endSession(SessionEndRequest request);
}