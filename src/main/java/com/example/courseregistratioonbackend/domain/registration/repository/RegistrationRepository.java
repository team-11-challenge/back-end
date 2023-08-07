package com.example.courseregistratioonbackend.domain.registration.repository;

import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByStudentIdAndCourseSubjectId(Long studentId, Long subjectId);
    List<Registration> findAllByStudentId(Long studentId);

    Optional<Registration> findByIdAndStudentId(Long registrationId, Long studentId);
}
