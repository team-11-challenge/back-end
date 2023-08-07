package com.example.courseregistratioonbackend.domain.registration.service;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.domain.registration.dto.RegistrationDto;
import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.registration.exception.SubjectAlreadyRegisteredException;
import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import com.example.courseregistratioonbackend.global.enums.SuccessCode;
import com.example.courseregistratioonbackend.global.parsing.entity.Belong;
import com.example.courseregistratioonbackend.global.parsing.entity.College;
import com.example.courseregistratioonbackend.global.parsing.entity.Professor;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.SUBJECT_ALREADY_REGISTERED;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.REGISTRATION_DELETE_SUCCESS;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.REGISTRATION_SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private RegistrationService registrationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("수강신청")
    void testRegister() {
        // given
        Long courseId = 1L;
        Long studentId = 1L;

        Student student = Student.builder()
                .id(studentId)
                .possibleCredits(6)
                .build();

        Subject subject = Subject.builder()
                .id(1L)
                .subjectCD(123456L)
                .subjectNM("운영체제")
                .build();

        Course course = Course.builder()
                .id(courseId)
                .credit(3)
                .limitation(2L)
                .subject(subject)
                .timetable("월 1 2,수 3")
                .build();

        Registration registration = Registration.builder()
                .course(course)
                .student(student)
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.ofNullable(course));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // when
        SuccessCode result = registrationService.register(1L, 1L);

        // then
        assertThat(result).isEqualTo(REGISTRATION_SUCCESS);
        verify(registrationRepository, times(1)).save(any(Registration.class));
        verify(courseRepository, times(1)).findById(courseId);
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    @DisplayName("수강신청_이미 신청한 교과목인 경우")
    public void testRegister_SubjectAlreadyRegistered() {
        // given
        Long courseId = 1L;
        Long studentId = 1L;

        Student student = Student.builder()
                .id(studentId)
                .build();

        Subject subject = Subject.builder()
                .id(1L)
                .build();

        Course course = Course.builder()
                .id(courseId)
                .subject(subject)
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.ofNullable(course));
        when(registrationRepository.existsByStudentIdAndCourseSubjectId(studentId, course.getSubject().getId())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> registrationService.register(courseId, studentId))
                .isInstanceOf(SubjectAlreadyRegisteredException.class)
                .hasMessageContaining(SUBJECT_ALREADY_REGISTERED.getDetail());
    }

    @Test
    @DisplayName("수강신청 취소")
    void testCancel() {
        // given
        Long registrationId = 1L;
        Long studentId = 1L;

        Course course = Course.builder()
                .id(1L)
                .limitation(2L)
                .build();

        Registration registration = Registration.builder()
                .course(course)
                .build();

        when(registrationRepository.findByIdAndStudentId(registrationId, studentId)).thenReturn(Optional.of(registration));

        // when
        SuccessCode result = registrationService.cancel(registrationId, studentId);

        // then
        assertThat(result).isEqualTo(REGISTRATION_DELETE_SUCCESS);
        verify(registrationRepository, times(1)).delete(registration);
    }

    @Test
    @DisplayName("수강신청 목록 조회")
    void testGetRegistration() {
        // given
        Long studentId = 1L;

        Student student = Student.builder()
                .id(studentId)
                .build();

        College college = College.builder()
                .id(1L)
                .build();

        Belong belong = Belong.builder()
                .id(1L)
                .college(college)
                .build();

        Professor professor = Professor.builder()
                .id(1L)
                .build();

        Subject subject = Subject.builder()
                .id(1L)
                .build();

        Subject subject2 = Subject.builder()
                .id(2L)
                .build();

        Course course = Course.builder()
                .id(1L)
                .belong(belong)
                .subject(subject)
                .professor(professor)
                .build();

        Course course2 = Course.builder()
                .id(2L)
                .belong(belong)
                .subject(subject2)
                .professor(professor)
                .build();

        Registration registration = Registration.builder()
                .id(1L)
                .student(student)
                .course(course)
                .build();

        Registration registration2 = Registration.builder()
                .id(2L)
                .student(student)
                .course(course2)
                .build();

        when(registrationRepository.findAllByStudentId(studentId)).thenReturn(Arrays.asList(registration, registration2));

        // when
        List<RegistrationDto> registrations = registrationService.getRegistration(studentId);

        // then
        assertThat(registrations).size().isEqualTo(2);
    }
}