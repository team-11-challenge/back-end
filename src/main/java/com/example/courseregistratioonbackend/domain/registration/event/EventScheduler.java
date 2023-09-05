package com.example.courseregistratioonbackend.domain.registration.event;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import com.example.courseregistratioonbackend.domain.registration.service.QueueService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventScheduler {
    private final QueueService queueService;

    @Scheduled(fixedDelay = 100)
    @SchedulerLock(
        name = "scheduledLockGetOrder",
        lockAtLeastFor = "1s",
        lockAtMostFor = "2s"
    )
    public void getOrder() throws JsonProcessingException {
        queueService.getOrder(Event.REGISTRATION);
    }

    @Scheduled(fixedDelay = 500)
    @SchedulerLock(
        name = "scheduledLockRegister",
        lockAtLeastFor = "4s",
        lockAtMostFor = "8s"
    )
    public void registerByScheduled() throws JsonProcessingException {
        queueService.publish(Event.REGISTRATION);
    }
}