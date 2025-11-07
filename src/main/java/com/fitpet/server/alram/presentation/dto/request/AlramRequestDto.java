package com.fitpet.server.alram.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlramRequestDto {

    @NotNull(message = "알림을 받을 사용자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "알림 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "알림 내용은 필수입니다.")
    private String body;

    private Map<String, String> data;
}