package com.example.courseregistratioonbackend.domain.student.repository;

import java.util.Optional;

import com.example.courseregistratioonbackend.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findByStudentNum(String studentNum);

}
