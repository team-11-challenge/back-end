package com.example.courseregistratioonbackend.domain.course.repository;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseYearAndSemesterAndSubjectAndDivision(int courseYear, int semester, Subject subject, int i);
}
