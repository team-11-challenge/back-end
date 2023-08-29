package com.example.courseregistratioonbackend.domain.registration.repository;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "RedisLockRepository")
@Component
@RequiredArgsConstructor
public class RedisLockRepository {

	@Value("${spring.data.redis.timeout}")
	private int time_out_length;

	private final RedisTemplate<String, String> redisTemplate;

	public Boolean getLock(final Long key) {
		return redisTemplate
			.opsForValue()
			.setIfAbsent(key.toString(), "lock", Duration.ofMillis(time_out_length));
	}

	public Boolean offLock(final Long key) {
		return redisTemplate.delete(key.toString());
	}
}
