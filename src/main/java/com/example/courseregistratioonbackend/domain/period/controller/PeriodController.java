package com.example.courseregistratioonbackend.domain.period.controller;

import com.example.courseregistratioonbackend.domain.period.dto.PeriodResponseDto;
import com.example.courseregistratioonbackend.domain.period.service.PeriodService;
import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.courseregistratioonbackend.global.enums.SuccessCode.TIMETABLE_GET_SUCCESS;

@Tag(name = "학사일정 관련 API", description = "학사일정 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PeriodController {
    private final PeriodService periodService;
    @GetMapping("/period")
    public ApiResponse<?> getAcademicCalendar(){
        PeriodResponseDto data = periodService.getPeriod();
        return ResponseUtils.ok(TIMETABLE_GET_SUCCESS, data);
    }
}
