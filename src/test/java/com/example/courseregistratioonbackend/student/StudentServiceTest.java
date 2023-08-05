package com.example.courseregistratioonbackend.student;

import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.dto.TimetableResponseDto;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.execption.StudentNotFoundException;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import com.example.courseregistratioonbackend.domain.student.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.NOT_FOUND_STUDENT;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentServiceTest {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RegistrationRepository registrationRepository;

    @Test
    @DisplayName("시간표 조회 테스트")
    public void getTimetable(){
        List<TimetableResponseDto> result = new ArrayList<>();
        try{
            //when
            Student student = studentRepository.findById(1L).orElseThrow(
                    () -> new StudentNotFoundException(NOT_FOUND_STUDENT)
            );
            //then
            List<Registration> registrationList = registrationRepository.findByStudent(student);

            for(int i = 0; i < registrationList.size(); i++){
                List<String[]> timetable = new ArrayList<>();

                String courseNM = registrationList.get(i).getCourse().getSubject().getSubjectNM();
                String timetableStr = registrationList.get(i).getCourse().getTimetable();
                String[] timetableStrList = timetableStr.split(",");

                for(String str : timetableStrList){
                    String[] oneCourse = str.split(" ");
                    timetable.add(oneCourse);
                }

                TimetableResponseDto timetableResponseDto = new TimetableResponseDto(courseNM, timetable);
                result.add(timetableResponseDto);
            }
            //actions
            assertEquals(result.size(), registrationRepository.findByStudent(student).size());


        }catch (Exception e){
            fail("문제있음");
        }
    }
}
