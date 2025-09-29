package com.fitpet.server.dailyworkout.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GpsLogDto {

    @NotNull(message = "세션 ID는 필수입니다.")
    private Long sessionId;

    @NotNull(message = "위도는 필수입니다.")
    private BigDecimal latitude;

    @NotNull(message = "경도는 필수입니다.")
    private BigDecimal longitude;

    private BigDecimal speed;

    private BigDecimal altitude;

    @NotNull(message = "기록 시간은 필수입니다.")
    private LocalDateTime recordedAt;
}