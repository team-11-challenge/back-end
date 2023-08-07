package com.example.courseregistratioonbackend.global.config;

import com.example.courseregistratioonbackend.global.security.jwt.JwtAuthenticationFilter;
import com.example.courseregistratioonbackend.global.security.jwt.JwtAuthorizationFilter;
import com.example.courseregistratioonbackend.global.security.jwt.JwtUtil;
import com.example.courseregistratioonbackend.global.security.userdetails.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtUtil jwtUtil;
	private final ObjectMapper objectMapper;
	private final CorsConfig corsConfig;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, objectMapper);
		filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
		filter.setFilterProcessesUrl("/api/auth/login"); // 로그인 확인 경로 설정
		return filter;
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
	}

	// 필터 체인
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// 기본 로그인 방식 해제
		http.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable);

		// CSRF 설정 해제
		http.csrf(AbstractHttpConfigurer::disable);

		// 기본 설정인 세션방식 대신 Jwt 방식을 사용하기 위한 설정
		http.sessionManagement((sessionManagement) ->
			sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);

		// 요청 관리
		http.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용
				.requestMatchers("/api/auth/**").permitAll() // api/auth/로 시작하는 요청 모두 접근 허가
				.requestMatchers(HttpMethod.GET, "/api/courses/**").permitAll() // 강의 조회 접근 허가
				.anyRequest().authenticated() // 그외 모든 요청 인증 처리
		);

		// 필터 관리
		http.addFilter(corsConfig.corsFilter());
		http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);

		return http.build();
	}
}
