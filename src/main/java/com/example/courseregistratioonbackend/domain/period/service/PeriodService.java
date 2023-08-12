package com.example.courseregistratioonbackend.domain.period.service;

import com.example.courseregistratioonbackend.domain.period.dto.PeriodResponseDto;
import com.example.courseregistratioonbackend.domain.period.entity.Period;
import com.example.courseregistratioonbackend.domain.period.exception.PeriodNotFoundException;
import com.example.courseregistratioonbackend.domain.period.repository.PeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.PERIOD_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PeriodService {
    private final PeriodRepository periodRepository;

    //현재 시간에 맞는 기간 가져오기
    public PeriodResponseDto getPeriod() {
        // 현재 기간
        LocalDateTime currentPeriod = LocalDateTime.now();

        List<Period> filteredPeriods = periodRepository.findAll().stream()
                .filter(period -> period.getStartTime().isBefore(currentPeriod) && period.getEndTime().isAfter(currentPeriod))
                .collect(Collectors.toList());

        if(filteredPeriods.isEmpty()) {
            throw new PeriodNotFoundException(PERIOD_NOT_FOUND);
        }
        return new PeriodResponseDto(filteredPeriods.get(0));
    }
}
