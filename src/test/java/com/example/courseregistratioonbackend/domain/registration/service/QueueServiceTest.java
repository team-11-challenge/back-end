package com.example.courseregistratioonbackend.domain.registration.service;

import com.example.courseregistratioonbackend.domain.registration.dto.RegistrationRequestDto;
import com.example.courseregistratioonbackend.domain.registration.event.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class QueueServiceTest {
    @Autowired
    private QueueService queueService;

    @Test
    void test() throws JsonProcessingException {
        RegistrationRequestDto requestDto = new RegistrationRequestDto(1, 1);
        queueService.addQueue(Event.REGISTRATION, requestDto);
        queueService.publish(Event.REGISTRATION);
    }

//    @Test
//    void test1() throws InterruptedException {
//        final Event cuurentEvent = Event.REGISTRATION;
//        final int executeNumber = 5000;
//
//        final ExecutorService executorService = Executors.newCachedThreadPool();
//        final CountDownLatch countDownLatch = new CountDownLatch(executeNumber);
//
//        final AtomicInteger successCount = new AtomicInteger();
//        final AtomicInteger failCount = new AtomicInteger();
//
//        // Execute your JPA query
//        for (int i = 0; i < executeNumber; i++) {
//            int studentId = i;
//            int courseId = i;
//            executorService.execute(() -> {
//                try {
//                    queueService.addQueue(cuurentEvent, new RegistrationRequestDto(studentId, courseId));
//                    successCount.getAndIncrement();
//                } catch (Exception e) {
//                    failCount.getAndIncrement();
//                }
//                countDownLatch.countDown();
//            });
//        }
//
//        countDownLatch.await();
//
//    }
}
