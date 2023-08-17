package com.example.courseregistratioonbackend.domain.student.controller;

import com.example.courseregistratioonbackend.domain.student.dto.StudentInfoDto;
import com.example.courseregistratioonbackend.domain.student.dto.TimetableResponseDto;
import com.example.courseregistratioonbackend.domain.student.service.StudentService;
import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.security.userdetails.UserDetailsImpl;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.courseregistratioonbackend.global.enums.SuccessCode.GET_USERINFO_SUCCESS;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.TIMETABLE_GET_SUCCESS;

@Tag(name = "학생 관련 API", description = "학생 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/students")
public class StudentController {
    final private StudentService studentService;

    @Operation(summary = "해당 학생 시간표 조회", description = "학생이 시간표 조회 시 수강 신청한 강의 조회")
    @Parameter(name = "studentId", description = "조회할 학생의 학생ID ")
    @GetMapping("/timetable")
    public ApiResponse<?> getTimetable(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<TimetableResponseDto> data = studentService.getTimetable(userDetails.getStudentUser().getId());
        return ResponseUtils.ok(TIMETABLE_GET_SUCCESS, data);
    }

    @Operation(summary = "해당 학생 정보 조회", description = "해당 학생 정보 조회")
    @Parameter(name = "studentId", description = "조회할 학생의 학생ID")
    @GetMapping("/info")
    public ApiResponse<?> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        StudentInfoDto data = studentService.getStudentInfo(userDetails.getStudentUser().getId());
        return ResponseUtils.ok(GET_USERINFO_SUCCESS, data);
    }
}
