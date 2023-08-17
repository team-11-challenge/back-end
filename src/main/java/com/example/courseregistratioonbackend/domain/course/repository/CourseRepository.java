package com.example.courseregistratioonbackend.domain.course.repository;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseYearAndSemesterAndSubjectAndDivision(int courseYear, int semester, Subject subject, int i);

    Optional<List<Course>> findAllBySubjectIdAndCourseYearAndSemester(Long subjectId, int courseYear, int semester);

    Optional<List<Course>> findAllByCourseYearAndSemesterAndBelongId(int courseYear, int semester, Long belongId);

    Optional<List<Course>> findAllByCourseYearAndSemesterAndSort(int courseYear, int semester, String sort);

    Optional<List<Course>> findAllByCourseYearAndSemester(int courseYear, int semester);
}
