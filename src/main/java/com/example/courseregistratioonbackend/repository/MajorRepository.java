package com.example.courseregistratioonbackend.repository;

import com.example.courseregistratioonbackend.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major, Long> {
    Major findByMajorNM(String majorNM);
}
