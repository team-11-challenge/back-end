package com.example.courseregistratioonbackend.repository;

import com.example.courseregistratioonbackend.entity.Belong;
import com.example.courseregistratioonbackend.entity.College;
import com.example.courseregistratioonbackend.entity.Department;
import com.example.courseregistratioonbackend.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BelongRepository extends JpaRepository<Belong, Long> {
    Belong findByCollegeAndDepartmentAndMajor(College college, Optional<Department> optionalDepartment, Optional<Major> optionalMajor);
}
