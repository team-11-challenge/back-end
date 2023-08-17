package com.example.courseregistratioonbackend.domain.registration.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class SubjectAlreadyRegisteredException extends GlobalException {
    public SubjectAlreadyRegisteredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
