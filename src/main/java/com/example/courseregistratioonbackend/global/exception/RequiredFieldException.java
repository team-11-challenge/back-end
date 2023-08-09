package com.example.courseregistratioonbackend.global.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;

public class RequiredFieldException extends GlobalException {
    public RequiredFieldException(ErrorCode errorCode) {
        super(errorCode);
    }
}
