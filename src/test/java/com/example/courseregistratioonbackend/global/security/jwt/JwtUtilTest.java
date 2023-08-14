package com.example.courseregistratioonbackend.global.security.jwt;

import static org.assertj.core.api.Assertions.*;

import java.security.Key;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.courseregistratioonbackend.global.security.exception.JwtExpirationException;

import io.jsonwebtoken.security.Keys;

class JwtUtilTest {

	private JwtUtil jwtUtil;
	private long tokenTime;
	private String secretKey;
	private Key key;

	@BeforeEach
	void setUp() {
		tokenTime = 1000L;
		secretKey = "WVdWM1ptRjNaV0ZsWm1GM1pXWmhkMlZtWVhkbFpnPT1hYXdlcmF3ZWF3ZWZhd2VmYXdlZg=="; // key 조건에 맞는 값 입력(Base64 인코딩 된 값), 길이조건

		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);

		// 기본 생성 -> 테스트에서 필요값이 있을 때는 각 테스트에서 생성자 주입.
		jwtUtil = new JwtUtil(tokenTime, secretKey);
	}

	@Test
	@DisplayName("jwt 생성, 추출 테스트")
	void createJwtTest() {

		// given
		String userNumber = "1234567";

		// when
		String createdJwt = jwtUtil.createToken(userNumber);
		String jwtValue = createdJwt.substring(7);
		String extractedJwt = jwtUtil.substringToken(createdJwt);

		// then
		assertThat(createdJwt).startsWith("Bearer ");
		assertThat(jwtValue).isEqualTo(extractedJwt);

	}

	@Test
	@DisplayName("jwt 유효시간 이전 테스트")
	void validationOfJwtExpirationBeforeTest() throws InterruptedException {

		// given
		JwtUtil testJwtUtil = new JwtUtil(1004,
			"WVdWM1ptRjNaV0ZsWm1GM1pXWmhkMlZtWVhkbFpnPT1hYXdlcmF3ZWF3ZWZhd2VmYXdlZg==");
		String userNumber = "7894561";

		String createdJwt = testJwtUtil.createToken(userNumber);
		String extractedJwt = testJwtUtil.substringToken(createdJwt);

		// when
		boolean result = testJwtUtil.validateToken(extractedJwt);

		// then
		assertThat(result).isTrue();

	}

	@Test
	@DisplayName("jwt 유효시간 이후 테스트")
	void validationOfJwtExpirationAfterTest() throws InterruptedException {

		// given
		JwtUtil testJwtUtil = new JwtUtil(10,
			"WVdWM1ptRjNaV0ZsWm1GM1pXWmhkMlZtWVhkbFpnPT1hYXdlcmF3ZWF3ZWZhd2VmYXdlZg==");
		String userNumber = "7894561";

		String createdJwt = testJwtUtil.createToken(userNumber);
		String extractedJwt = testJwtUtil.substringToken(createdJwt);

		// when
		Thread.sleep(1000);

		// then
		assertThatThrownBy(() -> testJwtUtil.validateToken(extractedJwt))
			.isInstanceOf(JwtExpirationException.class)
			.hasMessageContaining("유효시간 만료, 재인증이 필요합니다.");

	}

	@Test
	@DisplayName("Jwt 조작 검증 테스트")
	void tamperedTokenTest() {
		// given
		String userNumber = "7894561";
		String createdJwt = jwtUtil.createToken(userNumber);
		String extractedJwt = jwtUtil.substringToken(createdJwt);

		// when
		// 중간지점 한글자 조작
		String tamperedToken = extractedJwt.substring(0, extractedJwt.length() / 2) + "X" + extractedJwt.substring(extractedJwt.length() / 2 + 1);
		boolean result = jwtUtil.validateToken(tamperedToken);

		// then
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("Jwt 비밀키 변조 테스트")
	void secretKeyChangeTest() {
		// given
		// 다른 비밀키로 만든 Jwt
		JwtUtil testJwtUtil = new JwtUtil(1000, "WVdWM1ptRjNaV0ZsWm1GM1phd2VmYXdlZmVhd2VmYXdlZmF3ZWY=");
		String userNumber = "1234567";
		String createdJwt = testJwtUtil.createToken(userNumber);
		String extractedJwt = testJwtUtil.substringToken(createdJwt);

		// when
		// 미리 생성한 jwtUtil로 검증
		boolean result = jwtUtil.validateToken(extractedJwt);

		// then
		assertThat(result).isFalse();
	}

}