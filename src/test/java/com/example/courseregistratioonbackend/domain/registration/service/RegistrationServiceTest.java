package com.example.courseregistratioonbackend.domain.registration.service;

import com.example.courseregistratioonbackend.domain.basket.entity.Basket;
import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.domain.registration.dto.RegistrationDto;
import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.registration.exception.*;
import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import com.example.courseregistratioonbackend.global.enums.SuccessCode;
import com.example.courseregistratioonbackend.global.parsing.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.*;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.REGISTRATION_DELETE_SUCCESS;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.REGISTRATION_SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // @Mock 사용 설정
class RegistrationServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private RegistrationService registrationService;

    private List<Course> courses;
    private List<Student> students;
    @BeforeEach
    void setUp() {
        // list 생성
        courses = new ArrayList<>();
        students = new ArrayList<>();

        // 학생 정보
        for (int i = 1; i <= 3; i++) {
            Student student = Student.builder()
                    .id((long)i)
                    .studentNum("111111" + i)
                    .studentNM("홍길동" + i)
                    .password("password")
                    .possibleCredits((i == 3) ? 3 : 6)
                    .appliedCredits((i == 3) ? 3 : 0)  // 이수가능학점이 다 찬 경우
                    .build();

            students.add(student);
        }

        // Course 에 들어갈 소속, 과목, 교수
        for (int i = 1; i <= 3; i++) {

            // college 생성
            College college = College.builder()
                    .id((long)i)
                    .collegeCD(1000000 + (long)i)
                    .collegeNM("대학" + i)
                    .build();

            // department 생성
            Department department = Department.builder()
                    .id((long)i)
                    .departCD(2000000 + (long)i)
                    .departNM("학과" + i)
                    .build();

            // major 생성
            Major major = Major.builder()
                    .id((long)i)
                    .majorCD(3000000 + (long)i)
                    .majorNM("전공" + i)
                    .build();

            // belong 생성
            Belong belong = Belong.builder()
                    .id((long)i)
                    .department(department)
                    .college(college)
                    .major(major)
                    .build();

            // Subject 생성
            Subject subject = Subject.builder()
                    .id((long)i)
                    .subjectCD(4000000 + (long)i)
                    .subjectNM("과목" + i)
                    .build();

            // Professor 생성
            Professor professor = Professor.builder()
                    .id((long)i)
                    .professorNM("교수" + i)
                    .build();

            // 생성할 Course 데이터
            Course course = Course.builder()
                    .id((long)i)
                    .sort("구분" + i)
                    .division(1)
                    .credit(3)
                    .timetable((i == 2) ? "월 1,목 3" : "월 1 2,수 3") // 시간표 겹치는 경우
                    .limitation(3L)
                    .current((long) ((i == 3) ? 3 : 0)) // 정원이 다 찬 경우
                    .courseYear(2023)
                    .semester(2)
                    .belong(belong)
                    .subject(subject)
                    .professor(professor)
                    .build();

            courses.add(course);
        }
    }

    @Test
    @DisplayName("수강신청(성공)")
    void testRegister() {
        // given
        Student student = students.get(0);
        Course course = courses.get(0);
        int originalAppliedCredit = student.getAppliedCredits();
        long originalCurrent = course.getCurrent();

        when(studentRepository.findStudentByIdAndLock(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findCourseByIdAndLock(course.getId())).thenReturn(Optional.of(course));

        // when
        SuccessCode result = registrationService.register(course.getId(), student.getId());

        // then
        assertThat(result).isEqualTo(REGISTRATION_SUCCESS);
        assertThat(course.getCurrent()).isEqualTo(originalCurrent + 1); // 현재 수강 신청 인원 증가
        assertThat(student.getAppliedCredits() - originalAppliedCredit).isEqualTo(course.getCredit()); // 신청 학점 증가
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }

    @Test
    @DisplayName("수강신청(실패)_이미 신청한 교과목인 경우")
    public void testRegister_SubjectAlreadyRegistered() {
        // given
        Student student = students.get(0);
        Course course = courses.get(0);

        when(studentRepository.findStudentByIdAndLock(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findCourseByIdAndLock(course.getId())).thenReturn(Optional.of(course));
        when(registrationRepository.existsByStudentIdAndCourseSubjectId(student.getId(), course.getSubject().getId())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> registrationService.register(course.getId(), student.getId()))
                .isInstanceOf(SubjectAlreadyRegisteredException.class)
                .hasMessageContaining(SUBJECT_ALREADY_REGISTERED.getDetail());
    }

    @Test
    @DisplayName("수강신청(실패)_수강 정원 초과인 경우")
    public void testRegister_CourseAlreadyFulled() {
        Student student = students.get(0);
        Course course = courses.get(2); // 정원이 다 찬 강의

        when(studentRepository.findStudentByIdAndLock(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findCourseByIdAndLock(course.getId())).thenReturn(Optional.of(course));

        // when & then
        assertThatThrownBy(() -> registrationService.register(course.getId(), student.getId()))
                .isInstanceOf(CourseAlreadyFulledException.class)
                .hasMessageContaining(COURSE_ALREADY_FULLED.getDetail());
    }

    @Test
    @DisplayName("수강신청(실패)_이수가능학점 초과인 경우")
    public void testRegister_CreditExceeded() {
        Student student = students.get(2); // 이수가능학점이 다 찬 학생
        Course course = courses.get(0);

        when(studentRepository.findStudentByIdAndLock(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findCourseByIdAndLock(course.getId())).thenReturn(Optional.of(course));

        // when & then
        assertThatThrownBy(() -> registrationService.register(course.getId(), student.getId()))
                .isInstanceOf(CreditExceededException.class)
                .hasMessageContaining(CREDIT_EXCEEDED.getDetail());
    }

    @Test
    @DisplayName("수강신청(실패)_강의시간이 겹치는 경우")
    public void testRegister_CourseTimeConflict() {
        Student student = students.get(0);

        // 강의시간 겹치는 강의들
        Course course = courses.get(0);
        Course course2 = courses.get(1);

        Registration registration = Registration.builder()
                .course(course)
                .student(student)
                .build();

        List<Registration> registrations = new ArrayList<>();
        registrations.add(registration);

        when(studentRepository.findStudentByIdAndLock(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findCourseByIdAndLock(course2.getId())).thenReturn(Optional.of(course2));
        when(registrationRepository.findByStudent(student)).thenReturn(registrations);

        // when & then
        assertThatThrownBy(() -> registrationService.register(course2.getId(), student.getId()))
                .isInstanceOf(CourseTimeConflictException.class)
                .hasMessageContaining(COURSE_TIME_CONFLICT.getDetail());
    }

    @Test
    @DisplayName("수강신청 취소(성공)")
    void testCancel() {
        // given
        Student student = students.get(0);
        Course course = courses.get(0);

        Registration registration = Registration.builder()
                .id(1L)
                .course(course)
                .student(student)
                .build();

        when(registrationRepository.findByIdAndStudentId(registration.getId(), student.getId())).thenReturn(Optional.of(registration));

        // when
        SuccessCode result = registrationService.cancel(registration.getId(), student.getId());

        // then
        assertThat(result).isEqualTo(REGISTRATION_DELETE_SUCCESS);
        verify(registrationRepository, times(1)).delete(registration);
    }

    @Test
    @DisplayName("수강신청 취소(실패)_신청한 학생이 아닌 경우")
    void testCancel_NoAuthorityToRegistration() {
        // given
        Student student = students.get(0);
        Student student2 = students.get(1);
        Course course = courses.get(0);

        Registration registration = Registration.builder()
                .id(1L)
                .course(course)
                .student(student2)
                .build();

        when(registrationRepository.findByIdAndStudentId(registration.getId(), student.getId())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> registrationService.cancel(registration.getId(), student.getId()))
                .isInstanceOf(NoAuthorityToRegistrationException.class)
                .hasMessageContaining(NO_AUTHORITY_TO_REGISTRATION.getDetail());
    }

    @Test
    @DisplayName("수강신청 목록 조회")
    void testGetRegistration() {
        // given
        Student student = students.get(0);
        Course course = courses.get(0);
        Course course2 = courses.get(1);

        Registration registration = Registration.builder()
                .id(1L)
                .course(course)
                .student(student)
                .build();

        Registration registration2 = Registration.builder()
                .id(2L)
                .course(course2)
                .student(student)
                .build();

        when(registrationRepository.findByStudent(student)).thenReturn(Arrays.asList(registration, registration2));
        when(studentRepository.findStudentByIdAndLock(student.getId())).thenReturn(Optional.of(student));

        // when
        List<RegistrationDto> result = registrationService.getRegistration(student.getId());

        // then
        assertThat(result).size().isEqualTo(2);
    }
}