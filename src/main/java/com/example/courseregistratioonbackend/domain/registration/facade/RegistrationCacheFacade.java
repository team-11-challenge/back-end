package com.example.courseregistratioonbackend.domain.registration.facade;


import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.registration.dto.RegistrationRequestDto;
import com.example.courseregistratioonbackend.domain.registration.repository.RedisRepository;
import com.example.courseregistratioonbackend.domain.registration.service.RegistrationService;
import com.example.courseregistratioonbackend.global.enums.SuccessCode;
import com.example.courseregistratioonbackend.global.exception.RequiredFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.COURSE_ALREADY_FULLED;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.REGISTRATION_SUCCESS;

@Service
@RequiredArgsConstructor
public class RegistrationCacheFacade {

    private final RedisRepository redisRepository;

    private final RegistrationService registrationService;

    public SuccessCode registerByCache(RegistrationRequestDto requestDto) {
        // 서버 확인
        if (redisRepository.isRedisDown()){
            redisRepository.refreshLeftSeats();
        }

        // 만약 캐시에 인원이 0이라면
        if (redisRepository.hasLeftSeatsInRedis(requestDto.getCourseId()) && !redisRepository.checkLeftSeatInRedis(requestDto.getCourseId())){
            throw new RequiredFieldException(COURSE_ALREADY_FULLED);
        }

        // 실제 로직 수행
        Course course = registrationService.register(requestDto);

        // 만약 캐시에 없다면 캐시에 저장해줌
        if (!redisRepository.hasLeftSeatsInRedis(requestDto.getCourseId())){
            redisRepository.saveCourseToRedis(course);
        } else {
            //redis 값 변경
            redisRepository.decrementLeftSeatInRedis(requestDto.getCourseId());
        }

        return REGISTRATION_SUCCESS;
    }


}
