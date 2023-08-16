package com.example.courseregistratioonbackend.domain.course.repository;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @Description("과목 코드로 조회")
    void findAllBySubjectIdAndCourseYearAndSemester() {
        long startTime = System.currentTimeMillis();

        // Execute your JPA query
        List<Course> results = courseRepository.findAllBySubjectIdAndCourseYearAndSemester(556L, 2023, 1).orElseThrow();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Query execution time: " + executionTime + " ms");

        // Add assertions if needed
        assertNotNull(results);
    }

    @Test
    @Description("소속ID로 조회")
    void findAllByCourseYearAndSemesterAndBelongId() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        AtomicReference<List<Course>> results = new AtomicReference<>(new ArrayList<>());

        final int executeNumber = 5000;

        final ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(executeNumber);

        final AtomicInteger successCount = new AtomicInteger();
        final AtomicInteger failCount = new AtomicInteger();

        // Execute your JPA query
        for (int i = 0; i < executeNumber; i++) {
            executorService.execute(() -> {
                try {
                    results.set(courseRepository.findAllByCourseYearAndSemesterAndBelongId(2023, 1, 3L).orElseThrow());
                    successCount.getAndIncrement();
                } catch (Exception e) {
                    failCount.getAndIncrement();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Query execution time: " + executionTime + " ms");
        System.out.println("쿼리 성공: " + successCount.get());
        System.out.println("쿼리 실패: " + failCount.get());

        // Add assertions if needed
        assertEquals(failCount.get() + successCount.get(), executeNumber);
    }

    @Test
    @Description("구분으로 조회")
    void findAllByCourseYearAndSemesterAndSort() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        AtomicReference<List<Course>> results = new AtomicReference<>(new ArrayList<>());

        final int executeNumber = 5000;

        final ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(executeNumber);

        final AtomicInteger successCount = new AtomicInteger();
        final AtomicInteger failCount = new AtomicInteger();

        // Execute your JPA query
        for (int i = 0; i < executeNumber; i++) {
            executorService.execute(() -> {
                try {
                    results.set(courseRepository.findAllByCourseYearAndSemesterAndSort(2023, 1, "기초교양").orElseThrow());
                    successCount.getAndIncrement();
                } catch (Exception e) {
                    failCount.getAndIncrement();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Query execution time: " + executionTime + " ms");
        System.out.println("쿼리 성공: " + successCount.get());
        System.out.println("쿼리 실패: " + failCount.get());

        // Add assertions if needed
        assertEquals(failCount.get() + successCount.get(), executeNumber);
    }

    @Test
    @Description("년도 학기로만 검색")
    void findAllByCourseYearAndSemester() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        AtomicReference<List<Course>> results = new AtomicReference<>(new ArrayList<>());

        final int executeNumber = 5000;

        final ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(executeNumber);

        final AtomicInteger successCount = new AtomicInteger();
        final AtomicInteger failCount = new AtomicInteger();

        // Execute your JPA query
        for (int i = 0; i < executeNumber; i++) {
            executorService.execute(() -> {
                try {
                    results.set(courseRepository.findAllByCourseYearAndSemester(2023, 1).orElseThrow());
                    successCount.getAndIncrement();
                } catch (Exception e) {
                    failCount.getAndIncrement();
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Query execution time: " + executionTime + " ms");
        System.out.println("쿼리 성공: " + successCount.get());
        System.out.println("쿼리 실패: " + failCount.get());

        // Add assertions if needed
        assertEquals(failCount.get() + successCount.get(), executeNumber);
    }
}