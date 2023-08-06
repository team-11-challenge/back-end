package com.example.courseregistratioonbackend.domain.registration.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class CourseAlreadyFulledException extends GlobalException {
    public CourseAlreadyFulledException(ErrorCode errorCode) {
        super(errorCode);
    }
}
