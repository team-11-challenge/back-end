package com.example.courseregistratioonbackend.domain.registration.service;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.registration.exception.*;
import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import com.example.courseregistratioonbackend.global.enums.SuccessCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.*;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.REGISTRATION_DELETE_SUCCESS;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.REGISTRATION_SUCCESS;

@RequiredArgsConstructor
@Service
public class RegistrationService {
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public SuccessCode register(Long courseId, Long studentId) {
        // 학생 찾기
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new GlobalException(USER_NOT_FOUND));

        // 강의 찾기
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND));

        // 이미 신청한 교과목인지 확인
        boolean registered = registrationRepository.existsByStudentIdAndCourseSubjectId(student.getId(), course.getSubject().getId());
        if (registered) {
            throw new SubjectAlreadyRegisteredException(SUBJECT_ALREADY_REGISTERED);
        }

        // 수강 정원 이내 인지 확인
        if (course.getCurrent() + 1 > course.getLimitation()) {
            throw new CourseAlreadyFulledException(COURSE_ALREADY_FULLED);
        }

        // 학생 수강신청 리스트
        List<Registration> registrations = registrationRepository.findAllByStudentId(student.getId());

        // 이수가능학점이내 여부 확인
        int currentCredit = registrations.stream()
                .mapToInt(r -> r.getCourse().getCredit())
                .sum();
        if (student.getPossibleCredits() < currentCredit + course.getCredit()) {
            throw new CreditExceededException(CREDIT_EXCEEDED);
        }

        // 강의 시간이 겹치는 지 확인
        StringBuilder sb = new StringBuilder();
        for (Registration r : registrations) {
            sb.append(r.getCourse().getTimetable());
            sb.append(",");
        }
        int[] timetableOfCourse = makeBooleanTimetable(course.getTimetable());
        int[] timetableOfStudent = makeBooleanTimetable(sb.toString());
        compareTimetable(timetableOfCourse, timetableOfStudent);

        // 수강신청 저장
        Registration registration = Registration.builder()
                .student(student)
                .course(course)
                .build();
        registrationRepository.save(registration);

        // 현재 수강 신청 인원 증가
        course.addRegistration();

        return REGISTRATION_SUCCESS;
    }

    @Transactional
    public SuccessCode cancel(Long registrationId, Long studentId) {
        // 신청한 학생이 맞는지 확인
        Registration registration = registrationRepository.findByIdAndStudentId(registrationId, studentId)
                .orElseThrow(() -> new GlobalException(NO_AUTHORITY_TO_DATA)); // TODO: 나중에 exception 만들기

        // 수강신청 삭제
        registrationRepository.deleteById(registrationId);

        // 현재 수강 신청 인원 감소
        registration.getCourse().deleteRegistration();

        return REGISTRATION_DELETE_SUCCESS;
    }

    // 비교할 배열로 만들기
    private int[] makeBooleanTimetable(String rawTimetable) {
        enum DayOfWeek { 월, 화, 수, 목, 금, 토, 일 }
        int[] timetable = new int[7];
        for (String t : rawTimetable.split(",")) {
            int i = DayOfWeek.valueOf(t.substring(0, 1)).ordinal();
            String[] periods = t.substring(2).split(" ");
            for (String period : periods) {
                int p = 1 << Integer.parseInt(period);
                timetable[i] |= p;
            }
        }
        return timetable;
    }

    // 시간표 비교
    private static void compareTimetable(int[] timetable1, int[] timetable2) {
        for (int i = 0; i < 7; i++) {
            if ((timetable1[i] & timetable2[i]) > 0) {
                throw new CourseTimeConflictException(COURSE_TIME_CONFLICT);
            }
        }
    }

}
