package com.example.courseregistratioonbackend.repository;

import com.example.courseregistratioonbackend.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College, Long>{
    College findByCollegeNM(String collegeNM);
}
