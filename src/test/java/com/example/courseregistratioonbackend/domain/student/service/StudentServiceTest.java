package com.example.courseregistratioonbackend.domain.student.service;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.dto.TimetableResponseDto;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import com.example.courseregistratioonbackend.global.parsing.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    private List<Student> studentList;
    private List<Registration> registrationList;
    private List<Course> courseList;

    @BeforeEach
    void setUp(){
        registrationList = new ArrayList<>();
        courseList = new ArrayList<>();
        studentList = new ArrayList<>();
        // 학생 정보 저장
        for(int i = 0; i < 2; i++){
            Student student = Student.builder()
                    .id((long) i)
                    .studentNum("111111" + i)
                    .studentNM("학생명" + i)
                    .password("testpw"+ i)
                    .possibleCredits(18)
                    .appliedCredits(3 + i * 3)
                    .build();
            studentList.add(student);
        }


        // 강의 정보 저장
        for (int i = 1; i < 4; i++) {

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

            // Course 생성
            Course course = Course.builder()
                    .id((long) i)
                    .sort("구분" + i)
                    .division(1)
                    .credit(3)
                    .timetable((i == 1)? "월 1,목 3": "수 3,금 3 4")
                    .limitation(45L)
                    .basket(0L)
                    .current(1L)
                    .courseYear(2023)
                    .semester(1)
                    .belong(belong)
                    .subject(subject)
                    .professor(professor)
                    .build();

            courseList.add(course);
        }

        // 신청 정보 저장
        for(int i = 0; i < 2; i++){
            Registration registration = Registration.builder()
                    .id((long) i)
                    .student(i < 2? studentList.get(0) : studentList.get(1))
                    .course(courseList.get(i))
                    .build();
            registrationList.add(registration);
        }

    }
    @Test
    @DisplayName("시간표 조회가 잘 되는지")
    void getTimetable(){
        //given
        Long studentId = studentList.get(0).getId();
        List<Registration> registrationOneStudent = new ArrayList<>();
        for(int i = 0; i < registrationList.size(); i++){
            if(registrationList.get(i).getStudent().getId() == studentId){
                registrationOneStudent.add(registrationList.get(i));
            }
        }
        List<TimetableResponseDto> timetableResponseDtoList = new ArrayList<>();

        //when
        for(int i = 0; i < registrationOneStudent.size(); i++){
            List<String[]> timetable = new ArrayList<>();

            String courseNM = registrationList.get(i).getCourse().getSubject().getSubjectNM();
            String timetableStr = registrationList.get(i).getCourse().getTimetable();
            String[] timetableStrList = timetableStr.split(",");

            for(String str : timetableStrList){
                String[] oneCourse = str.split(" ");
                timetable.add(oneCourse);
            }

            TimetableResponseDto timetableResponseDto = new TimetableResponseDto(courseNM, timetable);
            timetableResponseDtoList.add(timetableResponseDto);
        }

        //then
        assertThat(timetableResponseDtoList.size()).isEqualTo(2);
    }

}
