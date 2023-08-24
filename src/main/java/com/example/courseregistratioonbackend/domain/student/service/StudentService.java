package com.example.courseregistratioonbackend.domain.student.service;

import com.example.courseregistratioonbackend.domain.course.entity.QCourse;
import com.example.courseregistratioonbackend.domain.registration.entity.QRegistration;
import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.dto.StudentInfoDto;
import com.example.courseregistratioonbackend.domain.student.dto.TimetableResponseDto;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.execption.StudentNotFoundException;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import com.example.courseregistratioonbackend.global.parsing.entity.QSubject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.STUDENT_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final RegistrationRepository registrationRepository;
    private final JPAQueryFactory queryFactory;

    // 시간표 조회
    public List<TimetableResponseDto> getTimetable(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(STUDENT_NOT_FOUND)
        );

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
        return timetableResponseDtoList;
    }


    public StudentInfoDto getStudentInfo(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(STUDENT_NOT_FOUND)
        );
        return new StudentInfoDto(student);
    }
}
