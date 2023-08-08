package com.example.courseregistratioonbackend.domain.basket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.courseregistratioonbackend.domain.basket.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
	Optional<Basket> findByCourseIdAndStudentId(Long courseId, Long studentId);

	List<Basket> findByStudentId(Long studentId);

}
