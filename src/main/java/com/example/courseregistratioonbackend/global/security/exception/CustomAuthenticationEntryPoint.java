package com.example.courseregistratioonbackend.global.security.exception;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.*;
import static jakarta.servlet.http.HttpServletResponse.*;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "미인증 관련 로그")
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {

		log.error("인증에 실패하였습니다.");

		Object errorCode = request.getAttribute("Exception");

		String jsonValue = getJsonValueByErrorCode(response, errorCode);

		response.getWriter().write(jsonValue);
	}

	private String getJsonValueByErrorCode(HttpServletResponse response, Object errorCode) throws IOException {
		response.setContentType("application/json");
		response.setStatus(SC_UNAUTHORIZED);
		response.setCharacterEncoding("UTF-8");

		// 해당하는 에러코드가 존재하면 해당 에러 출력
		if (errorCode != null) {
			if (errorCode.equals(JWT_PREFIX_ERROR)) {
				response.setStatus(SC_BAD_REQUEST);
				return objectMapper.writeValueAsString(ResponseUtils.error(JWT_PREFIX_ERROR));
			} else if (errorCode.equals(JWT_EXPIRATION)) {
				return objectMapper.writeValueAsString(ResponseUtils.error(JWT_EXPIRATION));
			}
		}

		// 해당하는 에러코드가 없으면 기본인증 에러 출력
		return objectMapper.writeValueAsString(ResponseUtils.error(UNAUTHORIZED_USER));
	}
}
