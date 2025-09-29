package com.fitpet.server.dailyworkout.presentation.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GpsLogResponse {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime recordedAt;
}