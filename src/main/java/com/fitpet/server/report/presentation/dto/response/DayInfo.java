package com.fitpet.server.report.presentation.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class DayInfo {
    private LocalDate date;
    private int count;
    @Setter
    private List<String> imageUrls;
}