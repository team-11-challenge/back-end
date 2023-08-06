package com.example.courseregistratioonbackend.global.parsing.repository;

import com.example.courseregistratioonbackend.global.parsing.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByProfessorNM(String name);
}
