package com.example.courseregistratioonbackend.repository;

import com.example.courseregistratioonbackend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartNM(String departmentNM);

    Department findByDepartNM(String departNM);
}
