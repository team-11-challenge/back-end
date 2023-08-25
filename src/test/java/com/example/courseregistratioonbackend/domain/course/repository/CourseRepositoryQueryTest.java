package com.example.courseregistratioonbackend.domain.course.repository;

import com.example.courseregistratioonbackend.domain.course.dto.CourseResponseDto;
import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.entity.QCourse;
import com.example.courseregistratioonbackend.domain.course.exception.CourseNotFoundException;
import com.example.courseregistratioonbackend.global.parsing.entity.*;
import com.example.courseregistratioonbackend.global.parsing.repository.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.COURSE_NOT_FOUND;

@SpringBootTest
@Transactional(readOnly = true)
public class CourseRepositoryQueryTest {
    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    CollegeRepository collegeRepository;

    @Autowired
    BelongRepository belongRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    MajorRepository majorRepository;

    @Autowired
    SubjectRepository subjectRepository;

    /**
     * 과목 코드로 조회
     */
    @RepeatedTest(10)
    @DisplayName("과목 코드로 조회 JPA")
    void getCourseListBySubjectCode_JPA(){
        int courseYear = 2023;
        int semester = 1;
        Long subjectCd = 1110003L;
        long startTime = System.currentTimeMillis();
        Subject subject = subjectRepository.findBySubjectCD(subjectCd).orElseThrow();
        List<Course> courses =
                courseRepository.findAllBySubjectIdAndCourseYearAndSemester(subject.getId(), courseYear, semester)
                        .orElseThrow(
                                () -> new CourseNotFoundException(COURSE_NOT_FOUND)
                        );

        courses.stream().map(CourseResponseDto::new).toList();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("size = " + courses.stream().map(CourseResponseDto::new).toList().size());
        System.out.println("Query execution time: " + executionTime + " ms");
    }

    @RepeatedTest(10)
    @DisplayName("과목 코드로 조회 QueryDSL")
    void getCourseListBySubjectCode_QueryDSL(){
        int courseYear = 2023;
        int semester = 1;
        Long subjectCd = 1110003L;
        long startTime = System.currentTimeMillis();
        QCourse qCourse = QCourse.course;
        QSubject qSubject = QSubject.subject;
        QProfessor qProfessor = QProfessor.professor;

        List<CourseResponseDto> courseResponseDtoList = queryFactory
                .select(qCourse)
                .from(qCourse)
                .join(qCourse.subject, qSubject).fetchJoin()
                .join(qCourse.professor, qProfessor).fetchJoin()
                .where(qSubject.subjectCD.eq(subjectCd)
                        .and(qCourse.courseYear.eq(courseYear))
                        .and(qCourse.semester.eq(semester)))
                .fetch()
                .stream()
                .map(CourseResponseDto::new)
                .toList();
        if(courseResponseDtoList.isEmpty()){
            throw new CourseNotFoundException(COURSE_NOT_FOUND);
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("size = " + courseResponseDtoList.size());
        System.out.println("Query execution time: " + executionTime + " ms");
    }
    /**
     * 구분으로 조회
     */
    @RepeatedTest(10)
    @DisplayName("구분으로 조회 JPA")
    void getCourseListBySortName_JPA(){
        int courseYear = 2023;
        int semester = 1;
        String sortNm = "기초교양";
        long startTime = System.currentTimeMillis();
        List<CourseResponseDto> courseResponseDtoList = courseRepository.findAllByCourseYearAndSemesterAndSort(courseYear, semester, sortNm).orElseThrow(
                        () -> new CourseNotFoundException(COURSE_NOT_FOUND)
                )
                .stream().map(CourseResponseDto::new).toList();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("size = " + courseResponseDtoList.size());
        System.out.println("Query execution time: " + executionTime + " ms");
    }

    @RepeatedTest(10)
    @DisplayName("구분으로 조회 QueryDSL")
    void getCourseListBySortName_QueryDSL(){
        int courseYear = 2023;
        int semester = 1;
        String sortNm = "기초교양";
        long startTime = System.currentTimeMillis();
        QCourse qCourse = QCourse.course;
        QProfessor qProfessor = QProfessor.professor;
        QSubject qSubject = QSubject.subject;

        List<CourseResponseDto> courseResponseDtoList = queryFactory
                .select(qCourse)
                .from(qCourse)
                .join(qCourse.professor, qProfessor).fetchJoin()
                .join(qCourse.subject, qSubject).fetchJoin()
                .where(qCourse.courseYear.eq(courseYear).and(qCourse.semester.eq(semester))
                        .and(qCourse.sort.eq(sortNm)))
                .fetch()
                .stream()
                .map(CourseResponseDto::new)
                .toList();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("size = " + courseResponseDtoList.size());
        System.out.println("Query execution time: " + executionTime + " ms");
    }
    /**
     * 대학으로 조회
     */
    @RepeatedTest(10)
    @DisplayName("대학으로 조회 JPA")
    void getCourseListByCollegeId_JPA(){
        int courseYear = 2023;
        int semester = 1;
        Long collegeId = 3L;
        String sortNm = "학문기초";
        long startTime = System.currentTimeMillis();
        College college = collegeRepository.findById(collegeId).orElseThrow();

        List<Belong> belongList = belongRepository.findAllByCollege(college).orElseThrow();

        getCourseResponseDtoList(courseYear, semester, belongList, sortNm);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("size = " + getCourseResponseDtoList(courseYear, semester, belongList, sortNm).size());
        System.out.println("Query execution time: " + executionTime + " ms");
    }

    @RepeatedTest(10)
    @DisplayName("대학으로 조회 QueryDSL")
    void getCourseListByCollegeId_QueryDSL(){
        int courseYear = 2023;
        int semester = 1;
        Long collegeId = 3L;
        String sortNm = "학문기초";

        long startTime = System.currentTimeMillis();

        QCourse qCourse = QCourse.course;
        QBelong qBelong = QBelong.belong;
        QProfessor qProfessor = QProfessor.professor;
        QSubject qSubject = QSubject.subject;

        List<CourseResponseDto> courseResponseDtoList = queryFactory
                .select(qCourse)
                .from(qCourse)
                .join(qCourse.belong, qBelong).fetchJoin()
                .join(qCourse.professor, qProfessor).fetchJoin()
                .join(qCourse.subject, qSubject).fetchJoin()
                .where(qBelong.college.id.eq(collegeId)
                        .and(qCourse.courseYear.eq(courseYear))
                        .and(qCourse.semester.eq(semester)))
                .fetch()
                .stream()
                .map(CourseResponseDto::new)
                .toList();

        if (courseResponseDtoList.size() < 1) throw new CourseNotFoundException(COURSE_NOT_FOUND);

        if (!sortNm.isBlank()) {
            courseResponseDtoList = courseResponseDtoList.stream()
                    .filter(course -> course.getSort().equals(sortNm))
                    .collect(Collectors.toList());
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("size = " + courseResponseDtoList.size());
        System.out.println("Query execution time: " + executionTime + " ms");
    }

    /**
     * 학과명으로 조회
     */
    @RepeatedTest(10)
    @DisplayName("학과명으로 조회 JPA")
    void getCourseListByDepartmentId_JPA(){
        int courseYear = 2023;
        int semester = 1;
        String sortNm = "전공선택";
        Long collegeId = 3L;
        Long departId = 2L;

        long startTime = System.currentTimeMillis();

        College college = collegeRepository.findById(collegeId).orElseThrow();
        Department department = departmentRepository.findById(departId).orElseThrow();

        List<Belong> belongList = belongRepository.findAllByCollegeAndDepartment(college, department).orElseThrow();
        Belong nullDepartBelong = belongRepository.findByCollegeAndDepartment(college, Optional.empty());
        if (nullDepartBelong != null) belongList.add(nullDepartBelong);

        getCourseResponseDtoList(courseYear, semester, belongList, sortNm);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("size = " + getCourseResponseDtoList(courseYear, semester, belongList, sortNm).size());
        System.out.println("Query execution time: " + executionTime + " ms");
    }

    @RepeatedTest(10)
    @DisplayName("학과명으로 조회 QueryDSL")
    void getCourseListByDepartmentId_QueryDSL(){
        int courseYear = 2023;
        int semester = 1;
        String sortNm = "전공선택";
        Long collegeId = 3L;
        Long departId = 2L;

        QCourse qCourse = QCourse.course;
        QBelong qBelong = QBelong.belong;
        QCollege qCollege = QCollege.college;
        QDepartment qDepartment = QDepartment.department;
        QProfessor qProfessor = QProfessor.professor;
        QSubject qSubject = QSubject.subject;

        long startTime = System.currentTimeMillis();

        List<CourseResponseDto> courseResponseDtoList = queryFactory.select(qCourse)
                .from(qCourse)
                .join(qCourse.belong, qBelong)
                .join(qBelong.college, qCollege)
                .join(qBelong.department, qDepartment)
                .join(qCourse.professor, qProfessor)
                .join(qCourse.subject, qSubject)
                .where(qCourse.courseYear.eq(courseYear)
                        .and(qCourse.semester.eq(semester))
                        .and(qBelong.college.id.eq(collegeId))
                        .and(qBelong.department.id.eq(departId)))
                .fetch()
                .stream()
                .map(CourseResponseDto::new)
                .toList();

        if (!sortNm.isBlank()) {
            courseResponseDtoList = courseResponseDtoList.stream()
                    .filter(course -> course.getSort().equals(sortNm))
                    .collect(Collectors.toList());
        }

        System.out.println("courseResponseDtoList.size() = " + courseResponseDtoList.size());

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("size = " + courseResponseDtoList.size());
        System.out.println("Query execution time: " + executionTime + " ms");
    }

    /**
     * 전공명으로 검색
     */
    @RepeatedTest(10)
    @DisplayName("전공명으로 조회 JPA")
    void getCourseList_JPA(){
        int courseYear = 2023;
        int semester = 1;
        String sortNm = "";
        Long collegeId = 3L;
        Long departId = 8L;
        Long majorId = 3L;

        long startTime = System.currentTimeMillis();

        College college = collegeRepository.findById(collegeId).orElseThrow();

        Department department = departmentRepository.findById(departId).orElse(null);


        Major major = majorRepository.findById(majorId).orElseThrow();

        List<Belong> belongList = belongRepository.findAllByCollegeAndDepartmentAndMajor(college, department, major).orElseThrow();

        Belong nullDepartBelong = belongRepository.findByCollegeAndDepartment(college, Optional.empty());
        Belong nullMajorBelong = belongRepository.findByCollegeAndDepartmentAndMajor(college, Optional.ofNullable(department), Optional.empty());
        if (nullDepartBelong != null) belongList.add(nullDepartBelong);
        if (nullMajorBelong != null) belongList.add(nullMajorBelong);

        getCourseResponseDtoList(courseYear, semester, belongList, sortNm);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("size = " + getCourseResponseDtoList(courseYear, semester, belongList, sortNm).size());
        System.out.println("Query execution time: " + executionTime + " ms");
    }

    @RepeatedTest(10)
    @DisplayName("전공명으로 조회 QueryDSL")
    void getCourseList_QueryDSL(){
        int courseYear = 2023;
        int semester = 1;
        String sortNm = "";
        Long collegeId = 3L;
        Long departId = 8L;
        Long majorId = 3L;

        long startTime = System.currentTimeMillis();

        QCourse qCourse = QCourse.course;
        QBelong qBelong = QBelong.belong;
        QCollege qCollege = QCollege.college;
        QProfessor qProfessor = QProfessor.professor;
        QSubject qSubject = QSubject.subject;

        List<CourseResponseDto> courseResponseDtoList = queryFactory
                .select(qCourse)
                .from(qCourse)
                .join(qCourse.belong, qBelong).fetchJoin()
                .join(qBelong.college, qCollege).fetchJoin()
                .join(qCourse.professor, qProfessor).fetchJoin()
                .join(qCourse.subject, qSubject).fetchJoin()
                .where(qCourse.courseYear.eq(courseYear)
                        .and(qCourse.semester.eq(semester))
                        .and(qBelong.college.id.eq(collegeId))
                        .and(qBelong.department.id.eq(departId).or(qBelong.department.isNull()))
                        .and(qBelong.major.id.eq(majorId).or(qBelong.major.isNull())))
                .fetch()
                .stream()
                .map(CourseResponseDto::new)
                .toList();
        if (!sortNm.isBlank()) {
            courseResponseDtoList = courseResponseDtoList.stream()
                    .filter(course -> course.getSort().equals(sortNm))
                    .collect(Collectors.toList());
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("size = " + courseResponseDtoList.size());
        System.out.println("Query execution time: " + executionTime + " ms");
    }

    private List<CourseResponseDto> getCourseResponseDtoList(int courseYear, int semester, List<Belong> belongList, String sortNm) {
        List<Course> courses = new ArrayList<>();
        belongList.forEach(belong -> {
            courseRepository.findAllByCourseYearAndSemesterAndBelongId(courseYear, semester, belong.getId()).ifPresent(courses::addAll);
        });

        if (courses.size() < 1) throw new CourseNotFoundException(COURSE_NOT_FOUND);

        if (sortNm.isBlank()) {
            return courses.stream().map(CourseResponseDto::new).toList();
        } else {
            return courses.stream().filter(course -> course.getSort().equals(sortNm)).map(CourseResponseDto::new).toList();
        }
    }
}
