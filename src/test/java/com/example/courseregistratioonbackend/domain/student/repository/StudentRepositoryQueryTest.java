package com.example.courseregistratioonbackend.domain.student.repository;

import com.example.courseregistratioonbackend.domain.course.entity.QCourse;
import com.example.courseregistratioonbackend.domain.registration.entity.QRegistration;
import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.dto.TimetableResponseDto;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.service.StudentService;
import com.example.courseregistratioonbackend.global.parsing.entity.QSubject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional(readOnly = true)
public class StudentRepositoryQueryTest {
    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    StudentService studentService;


    /**
     * 시간표 조회 시간 테스트
     */
    @RepeatedTest(10)
    @DisplayName("시간표 조회 시간 JPA")
    void getTableTime_JPA_10(){
        long startTime = System.currentTimeMillis();
        Student student = studentRepository.findById(1L).orElseThrow();

        List<Registration> registrationList = registrationRepository.findByStudent(student);
        List<TimetableResponseDto> timetableResponseDtoList =  registrationList.stream()
                .map(registration -> {
                    String courseNM = registration.getCourse().getSubject().getSubjectNM();
                    String[] timetableStrList = registration.getCourse().getTimetable().split(",");
                    List<String[]> timetable = new ArrayList<>();
                    for (String str : timetableStrList) {
                        timetable.add(str.split(" "));
                    }
                    return new TimetableResponseDto(courseNM, timetable);
                })
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Query execution time: " + executionTime + " ms");
    }

    @RepeatedTest(10)
    @DisplayName("시간표 조회 시간 QueryDSL")
    void getTableTime_QueryDSL_10(){
        long startTime = System.currentTimeMillis();
        Student student = studentRepository.findById(1L).orElseThrow();
        QRegistration qRegistration = QRegistration.registration;
        QCourse qCourse = QCourse.course;
        QSubject qSubject = QSubject.subject;
        List<Registration> registrationList = queryFactory
                .select(qRegistration)
                .from(qRegistration)
                .join(qRegistration.course, qCourse).fetchJoin()
                .join(qCourse.subject, qSubject).fetchJoin()
                .where(qRegistration.student.eq(student))
                .fetch();

        List<TimetableResponseDto> timetableResponseDtoList =  registrationList.stream()
                .map(registration -> {
                    String courseNM = registration.getCourse().getSubject().getSubjectNM();
                    String[] timetableStrList = registration.getCourse().getTimetable().split(",");
                    List<String[]> timetable = new ArrayList<>();
                    for (String str : timetableStrList) {
                        timetable.add(str.split(" "));
                    }
                    return new TimetableResponseDto(courseNM, timetable);
                })
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Query execution time: " + executionTime + " ms");
    }
}
