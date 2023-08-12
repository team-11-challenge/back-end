package com.example.courseregistratioonbackend.domain.student.service;

import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class StudentServiceTest {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    RegistrationRepository registrationRepository;


    @Test
    @DisplayName("시간표 조회 성공 테스트")
    void getTimetable(){
        Long studentId = 1L;
        int timetableListSize = studentService.getTimetable(studentId).size();
        assertEquals(2, timetableListSize);
    }
}
