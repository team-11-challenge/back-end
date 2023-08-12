package com.example.courseregistratioonbackend.global.security.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class UserNotFoundException extends GlobalException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
