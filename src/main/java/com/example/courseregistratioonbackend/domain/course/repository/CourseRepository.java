package com.example.courseregistratioonbackend.domain.course.repository;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<List<Course>> findAllBySubjectIdAndCourseYearAndSemester(Long subjectId, int courseYear, int semester);
    Course findByCourseYearAndSemesterAndSubjectAndDivision(int courseYear, int semester, Subject subject, int i);

    Optional<List<Course>> findAllByCourseYearAndSemesterAndBelongIdAndSort(int courseYear, int semester, Long belongId, String sort);
}
