package com.example.courseregistratioonbackend.domain.basket.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class AlreadyExistedInBasketException extends GlobalException {
	public AlreadyExistedInBasketException(ErrorCode errorCode) {
		super(errorCode);
	}
}
