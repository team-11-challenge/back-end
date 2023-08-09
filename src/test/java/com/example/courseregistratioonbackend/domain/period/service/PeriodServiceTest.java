package com.example.courseregistratioonbackend.domain.period.service;

import com.example.courseregistratioonbackend.domain.period.repository.PeriodRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@SpringBootTest
class PeriodServiceTest {
    @Autowired
    PeriodRepository periodRepository;

    @Autowired
    PeriodService periodService;

    @Test
    @DisplayName("기간 조회")
    public void getPeriodTest(){
        String periodNM = periodService.getPeriod().getPeriodNM();
        Assertions.assertThat(periodNM).isEqualTo("수강신청기간");
    }

}
