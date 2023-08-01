package com.example.courseregistratioonbackend.parsing;


import com.example.courseregistratioonbackend.entity.Course;
import com.example.courseregistratioonbackend.repository.CourseRepository;
import com.example.courseregistratioonbackend.service.CourseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CourseParserTest {
    @Autowired
    ReadLineContext<Course> courseReadLineContext;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Test
    @DisplayName("강의 정보 전체 add 잘 되는지")
    void addAllTest() {
        String filename = "src/main/resources/static/data/2023학년도 2학기 수업시간표.csv";
        int cnt = courseService.insertLargeVolumeCourseData(filename);
        assertEquals(cnt, courseRepository.count());
    }

    @Test
    @DisplayName("Course deleteAll, getCount 잘 되는지")
    void deleteAllTest(){
//        courseDao.deleteAll();
//        assertEquals(0, courseDao.getCount());
    }
}
