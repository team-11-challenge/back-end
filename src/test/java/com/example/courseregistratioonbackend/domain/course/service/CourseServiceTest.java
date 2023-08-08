package com.example.courseregistratioonbackend.domain.course.service;

import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.global.parsing.repository.BelongRepository;
import com.example.courseregistratioonbackend.global.parsing.repository.SubjectRepository;
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

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private BelongRepository belongRepository;

    @Test
    @DisplayName("과목코드로 조회")
    public void getCoursesBySubjectCode() {
        int courseListSize = courseService.getCourseList(2022, 1, 1110003L).size();
        assertEquals(21, courseListSize);
    }

    @Test
    @DisplayName("과목코드로 조회")
    public void getCoursesByOtherInfo() {
        int courseListSize = courseService.getCourseList(2023, 1, "역사교육과", "전공선택").size();
        assertEquals(14, courseListSize);
    }
}