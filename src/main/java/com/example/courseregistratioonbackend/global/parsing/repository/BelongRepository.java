package com.example.courseregistratioonbackend.global.parsing.repository;

import com.example.courseregistratioonbackend.global.parsing.entity.Belong;
import com.example.courseregistratioonbackend.global.parsing.entity.College;
import com.example.courseregistratioonbackend.global.parsing.entity.Department;
import com.example.courseregistratioonbackend.global.parsing.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BelongRepository extends JpaRepository<Belong, Long> {
    Belong findByCollegeAndDepartmentAndMajor(College college, Optional<Department> optionalDepartment, Optional<Major> optionalMajor);

    Belong findByCollegeAndDepartment(College college, Optional<Department> optionalDepartment);

    Optional<List<Belong>> findAllByCollegeAndDepartmentAndMajor(College college, Department department, Major major);

    Optional<List<Belong>> findAllByCollegeAndDepartment(College college, Department department);

    Optional<List<Belong>> findAllByCollege(College college);
}
