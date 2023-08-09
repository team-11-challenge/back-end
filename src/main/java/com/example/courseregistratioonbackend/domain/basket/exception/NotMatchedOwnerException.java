package com.example.courseregistratioonbackend.domain.basket.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class NotMatchedOwnerException extends GlobalException {
	public NotMatchedOwnerException(ErrorCode errorCode) {
		super(errorCode);
	}
}
