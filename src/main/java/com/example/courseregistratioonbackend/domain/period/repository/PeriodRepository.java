package com.example.courseregistratioonbackend.domain.period.repository;

import com.example.courseregistratioonbackend.domain.period.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PeriodRepository extends JpaRepository<Period, Long> {
}
