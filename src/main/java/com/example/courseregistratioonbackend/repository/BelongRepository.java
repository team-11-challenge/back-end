package com.example.courseregistratioonbackend.repository;

import com.example.courseregistratioonbackend.entity.Belong;
import com.example.courseregistratioonbackend.entity.College;
import com.example.courseregistratioonbackend.entity.Department;
import com.example.courseregistratioonbackend.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BelongRepository extends JpaRepository<Belong, Long> {
    Belong findByCollegeAndDepartmentAndMajor(College college, Department department, Major major);
}
