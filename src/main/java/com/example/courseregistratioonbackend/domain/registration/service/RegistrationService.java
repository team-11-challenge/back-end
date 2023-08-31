package com.example.courseregistratioonbackend.domain.registration.service;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.exception.CourseNotFoundException;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.domain.registration.dto.RegistrationDto;
import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.registration.exception.*;
import com.example.courseregistratioonbackend.domain.registration.repository.RedisRepository;
import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.execption.StudentNotFoundException;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import com.example.courseregistratioonbackend.global.enums.SuccessCode;
import com.example.courseregistratioonbackend.global.exception.RequiredFieldException;
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
    private final RedisRepository redisRepository;


    @Transactional
    public SuccessCode register(Long courseId, Long studentId) {
        // 서버 확인
        if(redisRepository.isRedisDown()){
            redisRepository.refreshLeftSeats();
        }

        // 만약 캐시에 인원이 0이라면
        if(redisRepository.hasLeftSeatsInRedis(courseId) && !redisRepository.checkLeftSeatInRedis(courseId)){
            throw new RequiredFieldException(COURSE_ALREADY_FULLED);
        }

        Student student = findStudentById(studentId);
        Course course = findCourseById(courseId);

        // 만약 캐시에 없다면 캐시에 저장해줌
        if(!redisRepository.hasLeftSeatsInRedis(courseId)){
            redisRepository.saveCourseToRedis(course);
        }

        // 신청 가능한지 여러 조건 확인
        checkIfSubjectAlreadyRegistered(student.getId(), course.getSubject().getId());
        checkCourseLimitation(course);
        checkCreditLimit(student, course);

        List<Registration> registrations = getRegistrationList(student);
        checkTimetable(course.getTimetable(), registrations);

        // 수강신청 저장
        registrationRepository.save(Registration.builder()
                .student(student)
                .course(course)
                .build());

        // 현재 수강 신청 인원 증가
        course.addRegistration();

        // 신청 학점 증가
        student.addRegistration(course.getCredit());

        //redis 값 변경
        redisRepository.decrementLeftSeatInRedis(courseId);

        return REGISTRATION_SUCCESS;
    }

    @Transactional
    public SuccessCode cancel(Long registrationId, Long studentId) {
        // 신청한 학생이 맞는지 확인
        Registration registration = registrationRepository.findByIdAndStudentId(registrationId, studentId)
                .orElseThrow(() -> new NoAuthorityToRegistrationException(NO_AUTHORITY_TO_REGISTRATION));

        // 수강신청 삭제
        registrationRepository.delete(registration);

        // 현재 수강 신청 인원 감소
        registration.getCourse().deleteRegistration();

        // 신청 학점 감소
        registration.getStudent().deleteRegistration(registration.getCourse().getCredit());


        // redis 해당 과목 인원 감소
        redisRepository.incrementLeftSeatInRedis(registration.getCourse().getId());

        return REGISTRATION_DELETE_SUCCESS;
    }

    public List<RegistrationDto> getRegistration(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                ()-> new StudentNotFoundException(STUDENT_NOT_FOUND)
        );
        List<Registration> registrationList = getRegistrationList(student);
        return registrationList.stream()
                .map(RegistrationDto::new)
                .toList();
    }

    private List<Registration> getRegistrationList(Student student) {
        return registrationRepository.findByStudent(student);
    }

    private Student findStudentById(Long studentId) {
        return studentRepository.findStudentByIdAndLock(studentId)
                .orElseThrow(() -> new StudentNotFoundException(STUDENT_NOT_FOUND));
    }

    private Course findCourseById(Long courseId) {
        return courseRepository.findCourseByIdAndLock(courseId)
                .orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND));
    }

    // 이미 신청한 교과목인지 확인
    private void checkIfSubjectAlreadyRegistered(Long studentId, Long subjectId) {
        boolean registered = registrationRepository.existsByStudentIdAndCourseSubjectId(studentId, subjectId);
        if (registered) {
            throw new SubjectAlreadyRegisteredException(SUBJECT_ALREADY_REGISTERED);
        }
    }

    // 이수가능학점이내 여부 확인
    private static void checkCreditLimit(Student student, Course course) {
        if (student.getPossibleCredits() < student.getAppliedCredits() + course.getCredit()) {
            throw new CreditExceededException(CREDIT_EXCEEDED);
        }
    }

    // 수강 정원 이내 인지 확인(-1인 경우는 확인 안함)
    private static void checkCourseLimitation(Course course) {
        if ((course.getLimitation() != -1) && (course.getCurrent() + 1 > course.getLimitation())) {
            throw new CourseAlreadyFulledException(COURSE_ALREADY_FULLED);
        }
    }

    // 현재 강의 시간이 신청가능한지 확인
    private void checkTimetable(String currentTimetable, List<Registration> registrations) {
        if (!registrations.isEmpty()) { // 수강신청 목록이 있다면
            // 강의 시간이 겹치는 지 확인
            StringBuilder sb = new StringBuilder();
            for (Registration r : registrations) {
                sb.append(r.getCourse().getTimetable());
                sb.append(",");
            }
            int[] timetableOfCourse = makeBooleanTimetable(currentTimetable);
            int[] timetableOfStudent = makeBooleanTimetable(sb.toString());
            compareTimetable(timetableOfCourse, timetableOfStudent);
        }
    }

    // 비교할 배열로 만들기
    private int[] makeBooleanTimetable(String rawTimetable) {
        enum DayOfWeek { 월, 화, 수, 목, 금, 토, 일 }
        int[] timetable = new int[7];
        for (String t : rawTimetable.split(",")) {
            int i = DayOfWeek.valueOf(t.substring(0, 1)).ordinal();
            String[] periods = t.substring(2).split(" ");
            for (String period : periods) {
                // 비트마스크 사용해 period 번째 비트에 1표시
                int p = 1 << Integer.parseInt(period);
                timetable[i] |= p;
            }
        }
        return timetable;
    }

    // 시간표 비교
    private static void compareTimetable(int[] timetable1, int[] timetable2) {
        for (int i = 0; i < 7; i++) {
            if ((timetable1[i] & timetable2[i]) > 0) { // 겹치는게 있을 경우 &연산시 0보다 큰값이 나옴
                throw new CourseTimeConflictException(COURSE_TIME_CONFLICT);
            }
        }
    }
}
