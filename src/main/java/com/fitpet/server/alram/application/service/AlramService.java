package com.fitpet.server.alram.application.service;

import com.fitpet.server.alram.presentation.dto.request.AlramRequestDto;
import com.fitpet.server.alram.presentation.dto.response.AlramResponseDto;

public interface AlramService {
    AlramResponseDto sendAndSaveAlram(AlramRequestDto requestDto);
}