package com.example.courseregistratioonbackend.parsing;

import com.example.courseregistratioonbackend.repository.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CourseParserTest {
    @Autowired
    ParsingService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Test
    @DisplayName("강의 정보 전체 add 잘 되는지")
    void addAllTest() {
        String filename = "src/main/resources/static/data/2023학년도 2학기 수업시간표.csv";
        courseService.insertData(filename);
    }

}
