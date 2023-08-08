package com.example.courseregistratioonbackend.domain.student.execption;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class StudentNotFoundException extends GlobalException {
    public StudentNotFoundException(ErrorCode errorCode){super(errorCode);}
}
