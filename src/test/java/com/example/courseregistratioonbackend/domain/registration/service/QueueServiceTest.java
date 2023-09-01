package com.example.courseregistratioonbackend.domain.registration.service;

import com.example.courseregistratioonbackend.domain.registration.dto.RegistrationRequestDto;
import com.example.courseregistratioonbackend.domain.registration.event.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class QueueServiceTest {
    @Autowired
    private QueueService queueService;

    @Test
    void test() throws JsonProcessingException {
        RegistrationRequestDto requestDto = new RegistrationRequestDto(1, 1, "abc");
        queueService.addQueue(Event.REGISTRATION, requestDto);
        queueService.publish(Event.REGISTRATION);
    }
}
