package com.example.courseregistratioonbackend.global.security.jwt;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.*;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.*;
import static jakarta.servlet.http.HttpServletResponse.*;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.courseregistratioonbackend.domain.student.dto.LoginRequestDto;
import com.example.courseregistratioonbackend.global.security.userdetails.UserDetailsImpl;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "로그인 및 JWT 발급")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtUtil jwtUtil;
	private final ObjectMapper objectMapper;

	// 로그인 시도
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		log.info("로그인 시도");
		try {
			LoginRequestDto requestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					requestDto.getStudentNum(),
					requestDto.getPassword(),
					null
				)
			);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	// 로그인 성공
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException {
		log.info("로그인 성공 및 JWT 생성");

		String userNumber = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
		log.info("userNumber: {}", userNumber);

		String token = jwtUtil.createToken(userNumber);
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

		setStatusCode(response, SC_OK);
		setContentApplicationJson(response);
		setCharEncoding(response);

		String jsonValue = objectMapper.writeValueAsString(ResponseUtils.ok(USER_LOGIN_SUCCESS));

		response.getWriter().write(jsonValue);
	}

	// 로그인 실패
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException {
		log.info("로그인 실패");

		setStatusCode(response, SC_BAD_REQUEST);
		setContentApplicationJson(response);
		setCharEncoding(response);

		// 기본적인 로그인 실패일 경우
		String jsonValue = objectMapper.writeValueAsString(ResponseUtils.error(USER_LOGIN_FAILURE));

		// 없는 Username의 경우
		if(failed instanceof UsernameNotFoundException) {
			setStatusCode(response, SC_NOT_FOUND);
			jsonValue = objectMapper.writeValueAsString(ResponseUtils.error(USER_NOT_FOUND));
		}

		response.getWriter().write(jsonValue);
	}

	// application/json 헤더설정 메서드
	private static void setContentApplicationJson(HttpServletResponse response) {
		response.setContentType("application/json");
	}

	// 상태코드 지정
	private static void setStatusCode(HttpServletResponse response, int statusCode) {
		response.setStatus(statusCode); // 상태코드
	}

	// UTF-8 지정
	private static void setCharEncoding(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8"); // utf-8 지정
	}
}
