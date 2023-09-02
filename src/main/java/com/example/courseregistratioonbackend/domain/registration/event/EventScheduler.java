package com.example.courseregistratioonbackend.domain.registration.event;

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

    @Scheduled(fixedDelay = 100)
    private void getOrder() throws JsonProcessingException {
        queueService.getOrder(Event.REGISTRATION);
    }

    @Scheduled(fixedDelay = 1000)
    private void register() throws JsonProcessingException {
        queueService.publish(Event.REGISTRATION);
    }
}