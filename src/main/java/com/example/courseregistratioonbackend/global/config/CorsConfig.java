package com.example.courseregistratioonbackend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	// cors 설정 필터
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*"); // 배포시 cors 적용할 주소 입력: secrets
		config.addAllowedHeader("*"); // 클라이언트가 보낸 헤더 중 서버에서 허용할 것
		config.addAllowedMethod("*");
		config.addExposedHeader("*"); // 응답시 클라이언트가 엑세스 할 수 있는 헤더
		config.setMaxAge(600L);
		source.registerCorsConfiguration("/**", config); // OriginPattern 의 모든 하위경로 cors 허용

		return new CorsFilter(source);
	}
}
