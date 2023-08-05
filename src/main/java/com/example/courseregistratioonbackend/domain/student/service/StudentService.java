package com.example.courseregistratioonbackend.domain.student.service;

import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.registration.repository.RegistrationRepository;
import com.example.courseregistratioonbackend.domain.student.dto.TimetableResponseDto;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.execption.StudentNotFoundException;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.NOT_FOUND_STUDENT;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final RegistrationRepository registrationRepository;

    // 시간표 조회
    public List<TimetableResponseDto> getTimetable(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(NOT_FOUND_STUDENT)
        );

        List<Registration> registrationList = registrationRepository.findByStudent(student);
        List<TimetableResponseDto> timetableResponseDtoList = new ArrayList<>();
        for(int i = 0; i < registrationList.size(); i++){
            String courseNM = registrationList.get(i).getCourse().getSubject().getSubjectNM();
            String timetableStr = registrationList.get(i).getCourse().getTimetable();
            List<String> timetable = List.of(timetableStr.split(","));
            TimetableResponseDto timetableResponseDto = new TimetableResponseDto(courseNM, timetable);
            timetableResponseDtoList.add(timetableResponseDto);
        }
        return timetableResponseDtoList;
    }
}
