package com.example.courseregistratioonbackend.domain.registration.scheduler;

import com.example.courseregistratioonbackend.domain.registration.event.Event;
import com.example.courseregistratioonbackend.domain.registration.service.QueueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventScheduler {
    private final QueueService queueService;

    @Scheduled(fixedDelay = 1000) // 1초 마다
    private void registrationEventScheduler() throws JsonProcessingException {
        queueService.publish(Event.REGISTRATION);
    }
}