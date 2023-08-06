package com.example.courseregistratioonbackend.global.security.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "jwtUtil 관련 로그")
@Component
public class JwtUtil {
	public static final String AUTHORIZATION_HEADER = "Authorization";   // Header의 KEY 값
	private final String tokenPrefix = "Bearer "; // Token 식별자
	private final long tokenTime;
	private final Key key;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	public JwtUtil(
		@Value("${jwt.token.expiration.time}") long tokenTime,
		@Value("${jwt.secret.key}") String secretKey) {

		this.tokenTime = tokenTime;
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(bytes);
	}

	// jwt 토큰 생성
	public String createToken(String userNumber) {
		Date date = new Date();

		return tokenPrefix
			+ Jwts.builder()
			.setSubject(userNumber)
			.setExpiration(new Date(date.getTime() + tokenTime))
			.setIssuedAt(date)
			.signWith(key, signatureAlgorithm)
			.compact();
	}

	// jwt check
	private boolean isIllegalJwt(String tokenValue) {
		return !StringUtils.hasText(tokenValue) || !tokenValue.startsWith(tokenPrefix);
	}

	// jwt parsing
	public String substringToken(String tokenValue) {
		if (isIllegalJwt(tokenValue)) {
			log.error("토큰이 존재하지 않거나 식별자가 유효하지 않습니다.");
			throw new IllegalArgumentException("토큰이 존재하지 않거나 식별자가 유효하지 않습니다.");
		}
		return tokenValue.substring(7);
	}

	// jwt validation
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); // key로 token 검증
			return true;
		} catch (SignatureException e) {
			log.error("키가 일치하지 않습니다.");
		} catch (SecurityException | MalformedJwtException e) {
			log.error("유효하지 않는 JWT 서명 입니다.");
		} catch (ExpiredJwtException e) {
			log.error("만료된 JWT token 입니다.");
		} catch (UnsupportedJwtException e) {
			log.error("지원되지 않는 JWT 토큰 입니다.");
		} catch (IllegalArgumentException e) {
			log.error("잘못된 JWT 토큰 입니다.");
		}
		return false;
	}

	// get information from token
	public Claims getClaimsFromToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	// token -> header
	public void addTokenToHeader(String token, HttpServletResponse response) {
		response.setHeader(AUTHORIZATION_HEADER, token);
	}

	// info -> userNumber
	public String getUserNumber(String token) {
		return getClaimsFromToken(token).getSubject();
	}

}


