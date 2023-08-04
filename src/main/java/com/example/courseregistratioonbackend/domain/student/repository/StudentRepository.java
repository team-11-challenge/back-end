package com.example.courseregistratioonbackend.domain.student.repository;

import com.example.courseregistratioonbackend.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
