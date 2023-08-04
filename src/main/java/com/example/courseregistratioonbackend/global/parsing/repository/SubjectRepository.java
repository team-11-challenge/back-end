package com.example.courseregistratioonbackend.global.parsing.repository;

import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findBySubjectCD(Long subjectCD);
}
