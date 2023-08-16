package com.example.courseregistratioonbackend.domain.course.controller;

import com.example.courseregistratioonbackend.domain.course.service.CourseService;
import com.example.courseregistratioonbackend.global.exception.RequiredFieldException;
import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.COLLEGE_NAME_IS_REQUIRED;

@Tag(name = "강의 조회", description = "조건별 강의 조회 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    /*
        과목코드 입력시 바로 강의 추출 가능하여 년도 학기 외 나머지 무시
        과목코드 미입력시 대학 필수, 구분 학과 전공 선택
        전공 입력시 학과 공통 과목 포함
     */
    @Operation(summary = "강의 조회", description = "여러 조건을 입력하여 해당하는 강의를 조회 합니다.")
    @Parameter(name = "courseYear", description = "년도")
    @GetMapping("/courses")
    public ApiResponse<?> getCourses(@RequestParam(value = "courseYear", defaultValue = "2023") int courseYear, // 년도
                                     @RequestParam(value = "semester", defaultValue = "1") int semester,        // 학기
                                     @RequestParam(value = "subjectCd", required = false) Long subjectCd,       // 과목코드
                                     @RequestParam(value = "collegeId", required = false) Long collegeId,       // 대학
                                     @RequestParam(value = "sortNm", required = false) String sortNm,           // 구분(전필, 전선, 교양 등)
                                     @RequestParam(value = "departId", required = false) Long departId,         // 학과
                                     @RequestParam(value = "majorId", required = false) Long majorId) {         // 전공

        if (subjectCd != null) {
            return ResponseUtils.ok(courseService.getCourseListBySubjectCode(courseYear, semester, subjectCd));
        }

        if (collegeId == null) {
            if (sortNm == null) {
                throw new RequiredFieldException(COLLEGE_NAME_IS_REQUIRED);
            } else {
                return ResponseUtils.ok(courseService.getCourseListBySortName(courseYear, semester, sortNm));
            }
        } else if (majorId == null) {
            if (departId == null) {
                return ResponseUtils.ok(courseService.getCourseListByCollegeId(courseYear, semester, collegeId, sortNm));
            } else {
                return ResponseUtils.ok(courseService.getCourseListByDepartmentId(courseYear, semester, collegeId, departId, sortNm));
            }
        } else {
            return ResponseUtils.ok(courseService.getCourseList(courseYear, semester, collegeId, departId, majorId, sortNm));
        }
    }
}
