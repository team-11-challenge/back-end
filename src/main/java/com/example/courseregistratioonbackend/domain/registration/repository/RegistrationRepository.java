package com.example.courseregistratioonbackend.domain.registration.repository;

import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByStudentIdAndCourseSubjectId(Long studentId, Long subjectId);



    List<Registration> findAllByStudentId(Long studentId);
}
