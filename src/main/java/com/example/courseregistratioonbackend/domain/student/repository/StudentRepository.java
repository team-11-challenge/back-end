package com.example.courseregistratioonbackend.domain.student.repository;

import com.example.courseregistratioonbackend.domain.student.entity.Student;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByStudentNum(String studentNum);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM Student s WHERE s.id = :studentId")
	Optional<Student> findStudentByIdAndLock(@Param("studentId")Long studentId);


}
