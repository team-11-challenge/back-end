package com.example.courseregistratioonbackend.domain.course.repository;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseYearAndSemesterAndSubjectAndDivision(int courseYear, int semester, Subject subject, int i);

    Optional<List<Course>> findAllBySubjectIdAndCourseYearAndSemester(Long subjectId, int courseYear, int semester);

    Optional<List<Course>> findAllByCourseYearAndSemesterAndBelongId(int courseYear, int semester, Long belongId);

    Optional<List<Course>> findAllByCourseYearAndSemesterAndSort(int courseYear, int semester, String sort);

    Optional<List<Course>> findAllByCourseYearAndSemester(int courseYear, int semester);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Course c WHERE c.id = :courseId")
	Optional<Course> findCourseByIdAndLock(@Param("courseId") Long courseId);

}
