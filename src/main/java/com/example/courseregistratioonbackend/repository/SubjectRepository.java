package com.example.courseregistratioonbackend.repository;

import com.example.courseregistratioonbackend.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findBySubjectCD(Long subjectCD);
}
