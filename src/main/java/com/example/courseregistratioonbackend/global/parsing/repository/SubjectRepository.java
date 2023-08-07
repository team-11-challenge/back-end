package com.example.courseregistratioonbackend.global.parsing.repository;

import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findBySubjectCD(Long subjectCd);
}
