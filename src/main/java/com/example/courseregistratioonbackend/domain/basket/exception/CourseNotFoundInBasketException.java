package com.example.courseregistratioonbackend.domain.basket.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class CourseNotFoundInBasketException extends GlobalException {
	public CourseNotFoundInBasketException(ErrorCode errorCode) {
		super(errorCode);
	}
}
