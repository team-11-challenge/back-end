package com.example.courseregistratioonbackend.domain.course.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Test
    @DisplayName("과목코드로 조회")
    public void getCourseListBySubjectCode() {
        int courseListSize = courseService.getCourseListBySubjectCode(2023, 1, 1110003L).size();
        assertEquals(21, courseListSize);
    }

    @Test
    @DisplayName("구분만 입력")
    void getCourseListBySortName() {
        int courseListSize = courseService.getCourseListBySortName(2023, 1, "기초교양").size();
        assertEquals(179, courseListSize);
    }

    @Test
    @DisplayName("입력 조건 전체 입력")
    void getCourseList1() {
        int courseListSize = courseService.getCourseList(2023, 1, 5L, 16L, 9L, "전공선택").size();
        assertEquals(29, courseListSize);
    }

    @Test
    @DisplayName("구분 외 입력 조건 전체 입력")
    void getCourseList2() {
        int courseListSize = courseService.getCourseList(2023, 1, 5L, 16L, 9L, "").size();
        assertEquals(66, courseListSize);
    }

    @Test
    @DisplayName("대학 입력")
    void getCourseListByCollegeName1() {
        int courseListSize = courseService.getCourseListByCollegeId(2023, 1, 5L, "전공선택").size();
        assertEquals(112, courseListSize);
    }

    @Test
    @DisplayName("대학 입력 구분 미입력")
    void getCourseListByCollegeName2() {
        int courseListSize = courseService.getCourseListByCollegeId(2023, 1, 5L, "").size();
        assertEquals(201, courseListSize);
    }

    @Test
    @DisplayName("학과 입력")
    void getCourseListByDepartmentName1() {
        int courseListSize = courseService.getCourseListByDepartmentId(2023, 1, 5L, 16L, "전공선택").size();
        assertEquals(50, courseListSize);
    }

    @Test
    @DisplayName("학과 입력 구분 미입력")
    void getCourseListByDepartmentName2() {
        int courseListSize = courseService.getCourseListByDepartmentId(2023, 1, 5L, 16L, "").size();
        assertEquals(103, courseListSize);
    }
}