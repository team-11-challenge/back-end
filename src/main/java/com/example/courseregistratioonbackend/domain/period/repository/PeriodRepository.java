package com.example.courseregistratioonbackend.domain.period.repository;

import com.example.courseregistratioonbackend.domain.period.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period, Long> {
}
