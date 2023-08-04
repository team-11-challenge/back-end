package com.example.courseregistratioonbackend.repository;

import com.example.courseregistratioonbackend.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByProfessorNM(String name);
}
