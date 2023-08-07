package com.example.courseregistratioonbackend.domain.course.controller;

import com.example.courseregistratioonbackend.domain.course.service.CourseService;
import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "강의 관련 API", description = "강의 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/courses")
    public ApiResponse<?> getCourses(@RequestParam(value = "courseYear", defaultValue = "2023") int courseYear,
                                     @RequestParam(value = "semester", defaultValue = "1") int semester,
                                     @RequestParam(value = "subjectCd", required = false) Long subjectCd,
                                     @RequestParam(value = "depart", required = false) String depart,
                                     @RequestParam(value = "sort", required = false) String sort) {
        if (subjectCd != null) {
            return ResponseUtils.ok(courseService.getCourseList(courseYear, semester, subjectCd));
        } else {
            return ResponseUtils.ok(courseService.getCourseList(courseYear, semester, depart, sort));
        }
    }
}
