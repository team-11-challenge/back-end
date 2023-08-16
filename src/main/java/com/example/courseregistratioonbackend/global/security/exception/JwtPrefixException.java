package com.example.courseregistratioonbackend.global.security.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class JwtPrefixException extends GlobalException {
	public JwtPrefixException(ErrorCode errorCode) {
		super(errorCode);
	}
}
