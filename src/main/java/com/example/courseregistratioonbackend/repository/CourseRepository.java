package com.example.courseregistratioonbackend.repository;

import com.example.courseregistratioonbackend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
