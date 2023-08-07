package com.example.courseregistratioonbackend.domain.course.service;

import com.example.courseregistratioonbackend.domain.course.dto.CourseResponseDto;
import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.exception.CourseNotFoundException;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.global.parsing.entity.Belong;
import com.example.courseregistratioonbackend.global.parsing.entity.Department;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import com.example.courseregistratioonbackend.global.parsing.repository.BelongRepository;
import com.example.courseregistratioonbackend.global.parsing.repository.DepartmentRepository;
import com.example.courseregistratioonbackend.global.parsing.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.COURSE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;
    private final BelongRepository belongRepository;

    public List<CourseResponseDto> getCourseList(int courseYear, int semester, Long subjectCd) {
        Subject subject = subjectRepository.findBySubjectCD(subjectCd).orElseThrow();

        List<Course> courses =
                courseRepository.findAllBySubjectIdAndCourseYearAndSemester(subject.getId(), courseYear, semester)
                        .orElseThrow(
                                () -> new CourseNotFoundException(COURSE_NOT_FOUND)
                        );

        return courses.stream().map(CourseResponseDto::new).toList();
    }

    public List<CourseResponseDto> getCourseList(int courseYear, int semester, String depart, String sort) {
        Department department = departmentRepository.findByDepartNM(depart);
        Belong belong = belongRepository.findByDepartmentId(department.getId()).orElseThrow();
        List<Course> courses =
                courseRepository.findAllByCourseYearAndSemesterAndBelongIdAndSort(courseYear, semester, belong.getId(), sort)
                        .orElseThrow(
                                () -> new CourseNotFoundException(COURSE_NOT_FOUND)
                        );

        return courses.stream().map(CourseResponseDto::new).toList();
    }
}
