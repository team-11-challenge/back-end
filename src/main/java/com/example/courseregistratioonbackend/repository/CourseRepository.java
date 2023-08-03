package com.example.courseregistratioonbackend.repository;

import com.example.courseregistratioonbackend.entity.Course;
import com.example.courseregistratioonbackend.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseYearAndSemesterAndSubjectAndDivision(int courseyear, int semester, Subject subject, int i);
}
