package com.example.courseregistratioonbackend.domain.course.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class CourseNotFoundException extends GlobalException {

    public CourseNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
