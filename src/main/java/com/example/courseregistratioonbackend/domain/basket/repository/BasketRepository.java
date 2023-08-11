package com.example.courseregistratioonbackend.domain.basket.repository;

import com.example.courseregistratioonbackend.domain.basket.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
	Optional<Basket> findByCourseIdAndStudentId(Long courseId, Long studentId);

	List<Basket> findByStudentId(Long studentId);

}
