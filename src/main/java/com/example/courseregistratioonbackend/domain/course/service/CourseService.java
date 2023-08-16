package com.example.courseregistratioonbackend.domain.course.service;

import com.example.courseregistratioonbackend.domain.course.dto.CourseResponseDto;
import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.exception.CourseNotFoundException;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.global.parsing.entity.*;
import com.example.courseregistratioonbackend.global.parsing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.COURSE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;
    private final BelongRepository belongRepository;
    private final MajorRepository majorRepository;
    private final CollegeRepository collegeRepository;

    // 과목 코드로 검색
    public List<CourseResponseDto> getCourseListBySubjectCode(int courseYear, int semester, Long subjectCd) {
        Subject subject = subjectRepository.findBySubjectCD(subjectCd).orElseThrow();

        List<Course> courses =
                courseRepository.findAllBySubjectIdAndCourseYearAndSemester(subject.getId(), courseYear, semester)
                        .orElseThrow(
                                () -> new CourseNotFoundException(COURSE_NOT_FOUND)
                        );

        return courses.stream().map(CourseResponseDto::new).toList();
    }

    // 구분 으로 검색
    public List<CourseResponseDto> getCourseListBySortName(int courseYear, int semester, String sortNm) {
        return courseRepository.findAllByCourseYearAndSemesterAndSort(courseYear, semester, sortNm).orElseThrow(
                        () -> new CourseNotFoundException(COURSE_NOT_FOUND)
                )
                .stream().map(CourseResponseDto::new).toList();
    }

    // 대학 으로 검색
    public List<CourseResponseDto> getCourseListByCollegeId(int courseYear, int semester, Long collegeId, String sortNm) {
        College college = collegeRepository.findById(collegeId).orElseThrow();

        List<Belong> belongList = belongRepository.findAllByCollege(college).orElseThrow();

        return getCourseResponseDtoList(courseYear, semester, belongList, sortNm);
    }


    // 학과명 으로 검색
    public List<CourseResponseDto> getCourseListByDepartmentId(int courseYear, int semester, Long collegeId, Long departId, String sortNm) {
        College college = collegeRepository.findById(collegeId).orElseThrow();
        Department department = departmentRepository.findById(departId).orElseThrow();

        List<Belong> belongList = belongRepository.findAllByCollegeAndDepartment(college, department).orElseThrow();
        Belong nullDepartBelong = belongRepository.findByCollegeAndDepartment(college, Optional.empty());
        if (nullDepartBelong != null) belongList.add(nullDepartBelong);

        return getCourseResponseDtoList(courseYear, semester, belongList, sortNm);
    }

    // 전공명 으로 검색 (학과는 없고 전공만 있는 소속이 있기 때문에 학과명을 입력 받는다 )
    public List<CourseResponseDto> getCourseList(int courseYear, int semester, Long collegeId, Long departId, Long majorId, String sortNm) {
        College college = collegeRepository.findById(collegeId).orElseThrow();
        Department department = departmentRepository.findById(departId).orElseThrow();
        Major major = majorRepository.findById(majorId).orElseThrow();

        List<Belong> belongList = belongRepository.findAllByCollegeAndDepartmentAndMajor(college, department, major).orElseThrow();

        Belong nullDepartBelong = belongRepository.findByCollegeAndDepartment(college, Optional.empty());
        Belong nullMajorBelong = belongRepository.findByCollegeAndDepartmentAndMajor(college, Optional.of(department), Optional.empty());
        if (nullDepartBelong != null) belongList.add(nullDepartBelong);
        if (nullMajorBelong != null) belongList.add(nullMajorBelong);

        return getCourseResponseDtoList(courseYear, semester, belongList, sortNm);
    }

    // Course -> DTO 변환
    private List<CourseResponseDto> getCourseResponseDtoList(int courseYear, int semester, List<Belong> belongList, String sortNm) {
        List<Course> courses = new ArrayList<>();
        belongList.forEach(belong -> {
            courseRepository.findAllByCourseYearAndSemesterAndBelongId(courseYear, semester, belong.getId()).ifPresent(courses::addAll);
        });

        if (courses.size() < 1) throw new CourseNotFoundException(COURSE_NOT_FOUND);

        if (sortNm == null) {
            return courses.stream().map(CourseResponseDto::new).toList();
        } else {
            return courses.stream().filter(course -> course.getSort().equals(sortNm)).map(CourseResponseDto::new).toList();
        }
    }
}
