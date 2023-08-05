package com.example.courseregistratioonbackend.domain.student.controller;

import com.example.courseregistratioonbackend.domain.student.dto.TimetableResponseDto;
import com.example.courseregistratioonbackend.domain.student.service.StudentService;
import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.courseregistratioonbackend.global.enums.SuccessCode.TIMETABLE_GET_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/students")
public class StudentController {
    final private StudentService studentService;
    @GetMapping("/timetable")
    public ApiResponse<?> getTimetable(){
        Long studentId = 1L;
        List<TimetableResponseDto> data = studentService.getTimetable(studentId);
        return ResponseUtils.ok(TIMETABLE_GET_SUCCESS, data);
    }
}
