package com.example.courseregistratioonbackend.domain.registration.service;

import com.example.courseregistratioonbackend.domain.registration.dto.RegistrationRequestDto;
import com.example.courseregistratioonbackend.domain.registration.event.Event;
import com.example.courseregistratioonbackend.domain.registration.facade.RegistrationCacheFacade;
import com.example.courseregistratioonbackend.global.enums.SuccessCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import static com.example.courseregistratioonbackend.domain.registration.event.Event.REGISTRATION_RESULT;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.REGISTRATION_REQUEST_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final RegistrationCacheFacade registrationCacheFacade;
    //    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private static final long FIRST_ELEMENT = 0;
    //    private static final long LAST_ELEMENT = -1;
    private static final long PUBLISH_SIZE = 100;
    private static final long LAST_INDEX = 1;

    public SuccessCode addQueue(Event event, RegistrationRequestDto requestDto) throws JsonProcessingException {
        final String member = objectMapper.writeValueAsString(requestDto);
        final long now = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(event.toString(), member, now);
        log.info("대기열에 추가 - {} ({}초)", requestDto.getStudentId(), now);

        return REGISTRATION_REQUEST_SUCCESS;
    }

    public void publish(Event event) throws JsonProcessingException {
        final long start = FIRST_ELEMENT;
        final long end = PUBLISH_SIZE - LAST_INDEX;
        Set<String> queue = redisTemplate.opsForZSet().range(event.toString(), start, end);
        Objects.requireNonNull(queue).parallelStream().forEach(member -> {
            redisTemplate.opsForZSet().remove(event.toString(), member);
            RegistrationRequestDto requestDto = null;
            String result;
            try {
                requestDto = objectMapper.readValue(member, RegistrationRequestDto.class);
                SuccessCode code = registrationCacheFacade.registerByCache(requestDto);
                result = code.getDetail();
            } catch (GlobalException e) {
                result = e.getMessage();
            } catch (Exception e) {
                result = "죄송합니다. 서버에 장애가 발생하였습니다.";
                log.error("pt_cerror" + e.getMessage());
            }
            log.info("'{}'님의 registration 요청이 성공적으로 수행되었습니다.", requestDto.getStudentId());

            final long now = System.currentTimeMillis();
            redisTemplate.opsForZSet().add(REGISTRATION_RESULT.toString(), requestDto.getStudentNum() + "--pt--" + result, now);
        });

//        for (String member : queue) {
//            RegistrationRequestDto requestDto = objectMapper.readValue(member, RegistrationRequestDto.class);
//            String result;
//            try {
//                SuccessCode code = registrationCacheFacade.registerByCache(requestDto);
//                result = code.getDetail();
//            } catch (GlobalException e) {
//                result = e.getMessage();
//            } catch (Exception e) {
//                result = "죄송합니다. 서버에 장애가 발생하였습니다.";
//                log.error(e.getMessage());
//            }
//            log.info("'{}'님의 registration 요청이 성공적으로 수행되었습니다.", requestDto.getStudentId());
//            redisTemplate.opsForZSet().remove(event.toString(), member);
//
//            final long now = System.currentTimeMillis();
//            redisTemplate.opsForZSet().add(REGISTRATION_RESULT.toString(), requestDto.getStudentNum() + "--pt--" + result, now);


//            simpMessageSendingOperations.convertAndSend("/sub/result/" + requestDto.getStudentNM(), result);
//        }
    }

//    public void getOrder(Event event) throws JsonProcessingException {
//        final long start = FIRST_ELEMENT;
//        final long end = LAST_ELEMENT;
//
//        Set<String> queue = redisTemplate.opsForZSet().range(event.toString(), start, end);
//
//        for (String member : queue) {
//            Long rank = redisTemplate.opsForZSet().rank(event.toString(), member);
//            if (rank != null) {
//                RegistrationRequestDto requestDto = objectMapper.readValue(member, RegistrationRequestDto.class);
//                log.info("'{}'님의 현재 대기열은 {}명 남았습니다.", requestDto.getStudentId(), rank);
//                simpMessageSendingOperations.convertAndSend("/sub/order/" + requestDto.getStudentNM(), rank);
//            }
//        }
//    }

}