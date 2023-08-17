package com.example.courseregistratioonbackend.global.parsing.service;

import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class ParsingServiceTest {

    @Autowired
    ParsingService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Test
    @Rollback
    @DisplayName("강의 정보 전체 add 잘 되는지")
    void addAllTest() {
//        String filename = "src/main/resources/static/data/2022학년도 1학기 수업시간표.csv";
//        courseService.insertData(filename, 2022, 1);
    }
}