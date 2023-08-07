package com.example.courseregistratioonbackend.domain.course.repository;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseYearAndSemesterAndSubjectAndDivision(int courseYear, int semester, Subject subject, int i);
}
