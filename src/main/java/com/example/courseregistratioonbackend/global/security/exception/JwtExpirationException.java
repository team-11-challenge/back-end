package com.example.courseregistratioonbackend.global.security.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class JwtExpirationException extends GlobalException {
	public JwtExpirationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
