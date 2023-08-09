package com.example.courseregistratioonbackend.domain.period.dto;

import com.example.courseregistratioonbackend.domain.period.entity.Period;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PeriodResponseDto {
    private String periodNM;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public PeriodResponseDto(Period period){
        this.periodNM = period.getPeriodNM();
        this.startTime = period.getStartTime();
        this.endTime = period.getEndTime();
    }
}
