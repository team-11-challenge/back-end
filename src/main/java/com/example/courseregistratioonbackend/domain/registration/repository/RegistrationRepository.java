package com.example.courseregistratioonbackend.domain.registration.repository;

import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByStudentIdAndCourseSubjectId(Long studentId, Long subjectId);
    List<Registration> findByStudent(Student student);
    Optional<Registration> findByIdAndStudentId(Long registrationId, Long studentId);

    Long countByCourseId(Long courseId);
}
