package com.example.courseregistratioonbackend.global.parsing.repository;

import com.example.courseregistratioonbackend.global.parsing.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartNM(String departmentNM);

    Department findByDepartNM(String departNM);
}
