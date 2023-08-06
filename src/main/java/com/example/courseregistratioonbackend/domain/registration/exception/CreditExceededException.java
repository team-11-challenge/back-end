package com.example.courseregistratioonbackend.domain.registration.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class CreditExceededException extends GlobalException {
    public CreditExceededException(ErrorCode errorCode) {
        super(errorCode);
    }
}
