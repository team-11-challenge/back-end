package com.example.courseregistratioonbackend.domain.period.exception;

import com.example.courseregistratioonbackend.global.enums.ErrorCode;
import com.example.courseregistratioonbackend.global.exception.GlobalException;

public class PeriodNotFoundException extends GlobalException {
    public PeriodNotFoundException (ErrorCode errorCode) {
        super(errorCode);
    }
}
