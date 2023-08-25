package com.example.courseregistratioonbackend.domain.course.service;

import com.example.courseregistratioonbackend.domain.course.dto.CourseResponseDto;
import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.entity.QCourse;
import com.example.courseregistratioonbackend.domain.course.exception.CourseNotFoundException;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.global.parsing.entity.*;
import com.example.courseregistratioonbackend.global.parsing.repository.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.COURSE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {
    private final JPAQueryFactory queryFactory;

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;
    private final BelongRepository belongRepository;
    private final MajorRepository majorRepository;
    private final CollegeRepository collegeRepository;

    // 과목 코드로 검색
    public List<CourseResponseDto> getCourseListBySubjectCode(int courseYear, int semester, Long subjectCd) {
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

        return courseResponseDtoList;
    }

    // 구분 으로 검색
    public List<CourseResponseDto> getCourseListBySortName(int courseYear, int semester, String sortNm) {
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

        if(courseResponseDtoList.isEmpty()){
            throw new CourseNotFoundException(COURSE_NOT_FOUND);
        }

        return courseResponseDtoList;
    }

    // 대학 으로 검색
    public List<CourseResponseDto> getCourseListByCollegeId(int courseYear, int semester, Long collegeId, String sortNm) {
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

        return courseResponseDtoList;
    }


    // 학과명 으로 검색
    public List<CourseResponseDto> getCourseListByDepartmentId(int courseYear, int semester, Long collegeId, Long departId, String sortNm) {
        QCourse qCourse = QCourse.course;
        QBelong qBelong = QBelong.belong;
        QCollege qCollege = QCollege.college;
        QDepartment qDepartment = QDepartment.department;
        QProfessor qProfessor = QProfessor.professor;
        QSubject qSubject = QSubject.subject;

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
        return  courseResponseDtoList;
    }

    // 전공명 으로 검색 (학과는 없고 전공만 있는 소속이 있기 때문에 학과명을 입력 받는다 )
    public List<CourseResponseDto> getCourseList(int courseYear, int semester, Long collegeId, Long departId, Long majorId, String sortNm) {
        QCourse qCourse = QCourse.course;
        QBelong qBelong = QBelong.belong;
        QCollege qCollege = QCollege.college;
        QProfessor qProfessor = QProfessor.professor;
        QSubject qSubject = QSubject.subject;

        List<CourseResponseDto> courseResponseDtoList;
        if(departId == null) {
            courseResponseDtoList = queryFactory
                    .select(qCourse)
                    .from(qCourse)
                    .join(qCourse.belong, qBelong).fetchJoin()
                    .join(qBelong.college, qCollege).fetchJoin()
                    .join(qCourse.professor, qProfessor).fetchJoin()
                    .join(qCourse.subject, qSubject).fetchJoin()
                    .where(qCourse.courseYear.eq(courseYear)
                            .and(qCourse.semester.eq(semester))
                            .and(qBelong.college.id.eq(collegeId))
                            .and(qBelong.major.id.eq(majorId).or(qBelong.major.isNull())))
                    .fetch()
                    .stream()
                    .map(CourseResponseDto::new)
                    .toList();
        }else{
            courseResponseDtoList = queryFactory
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
        }

        if (!sortNm.isBlank()) {
            courseResponseDtoList = courseResponseDtoList.stream()
                    .filter(course -> course.getSort().equals(sortNm))
                    .collect(Collectors.toList());
        }
        return courseResponseDtoList;
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
