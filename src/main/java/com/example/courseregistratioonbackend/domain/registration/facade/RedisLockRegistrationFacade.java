package com.example.courseregistratioonbackend.domain.registration.facade;

import org.springframework.stereotype.Component;

import com.example.courseregistratioonbackend.domain.registration.repository.RedisLockRepository;
import com.example.courseregistratioonbackend.domain.registration.service.RegistrationService;
import com.example.courseregistratioonbackend.global.enums.SuccessCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "RedisService")
@Component
@RequiredArgsConstructor
public class RedisLockRegistrationFacade {

	private final RedisLockRepository redisLockRepository;

	private final RegistrationService registrationService;

	public SuccessCode registerByRedisLock(Long courseId, Long studentId) throws InterruptedException {

		while (!redisLockRepository.getLock(courseId)) {
			Thread.sleep(100); // redis 부하 조절
		}

		try {
			return registrationService.register(courseId, studentId);
		} finally {
			redisLockRepository.offLock(courseId);
		}

	}

}
