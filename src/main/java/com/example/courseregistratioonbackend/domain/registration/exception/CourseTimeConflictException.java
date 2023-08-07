package com.example.courseregistratioonbackend.domain.registration.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class CourseTimeConflictException extends GlobalException {
    public CourseTimeConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
