package com.example.courseregistratioonbackend.domain.student.service;

import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.dto.StudentInfoDto;
import com.example.courseregistratioonbackend.domain.student.dto.TimetableResponseDto;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.execption.StudentNotFoundException;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.STUDENT_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final RegistrationRepository registrationRepository;

    // 시간표 조회
    public List<TimetableResponseDto> getTimetable(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(STUDENT_NOT_FOUND)
        );

        List<Registration> registrationList = registrationRepository.findByStudent(student);
        List<TimetableResponseDto> timetableResponseDtoList = new ArrayList<>();
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
            timetableResponseDtoList.add(timetableResponseDto);
        }
        return timetableResponseDtoList;
    }

    public StudentInfoDto getStudentInfo(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(STUDENT_NOT_FOUND)
        );
        return new StudentInfoDto(student);
    }
}
