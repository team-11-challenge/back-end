package com.example.courseregistratioonbackend.domain.registration.service;

import com.example.courseregistratioonbackend.domain.registration.dto.RegistrationRequestDto;
import com.example.courseregistratioonbackend.domain.registration.event.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final RegistrationService registrationService;
    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1;
    private static final long PUBLISH_SIZE = 10;
    private static final long LAST_INDEX = 1;

    public void addQueue(Event event, RegistrationRequestDto requestDto) throws JsonProcessingException {
        final String member = objectMapper.writeValueAsString(requestDto);
        final long now = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(event.toString(), member, now);
        log.info("대기열에 추가 - {} ({}초)", requestDto.getStudentId(), now);
    }

    public void publish(Event event) throws JsonProcessingException {
        final long start = FIRST_ELEMENT;
        final long end = PUBLISH_SIZE - LAST_INDEX;
        Set<String> queue = redisTemplate.opsForZSet().range(event.toString(), start, end);
        for (String member : queue) {
//            registrationService.register(request);
            RegistrationRequestDto requestDto = objectMapper.readValue(member, RegistrationRequestDto.class);
            log.info("'{}'님의 registration 요청이 성공적으로 수행되었습니다.", requestDto.getStudentId());
            // TODO: 결과를 받아서 클라이언트로 응답해줘야함
            redisTemplate.opsForZSet().remove(event.toString(), member);
        }
    }

    public void getOrder(Event event){
        final long start = FIRST_ELEMENT;
        final long end = LAST_ELEMENT;

        Set<String> queue = redisTemplate.opsForZSet().range(event.toString(), start, end);

        for (Object request : queue) {
            Long rank = redisTemplate.opsForZSet().rank(event.toString(), request);
            log.info("'{}'님의 현재 대기열은 {}명 남았습니다.", request, rank);
        }
    }

    public long getSize(Event event){
        Long size = redisTemplate.opsForZSet().size(event.toString());
        return (size != null) ? size : 0;
    }
}