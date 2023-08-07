package com.example.courseregistratioonbackend.domain.registration.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class NoAuthorityToRegistrationException extends GlobalException {
    public NoAuthorityToRegistrationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
