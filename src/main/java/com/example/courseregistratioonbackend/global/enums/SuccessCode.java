package com.example.courseregistratioonbackend.global.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
public enum SuccessCode {
  
    /* 200 OK : 요청이 성공적으로 완료되었다는 의미입니다. */
    USER_LOGIN_SUCCESS(OK, "로그인 성공했습니다."),
    GET_USERINFO_SUCCESS(OK, "학생 정보 조회 성공했습니다."),
    TIMETABLE_GET_SUCCESS(OK, "시간표 조회 성공했습니다."),
    DELETE_COURSE_FROM_BASKET_SUCCESS(OK, "예비수강과목 삭제 성공했습니다."),
    REGISTRATION_DELETE_SUCCESS(OK, "해당 신청이 성공적으로 취소되었습니다."),

    /* 201 CREATED : 요청이 성공적이었으며 그 결과로 새로운 리소스가 생성 되었다는 의미입니다. */
    ADD_COURSE_TO_BASKET_SUCCESS(CREATED, "예비수강과목 담기 성공했습니다."),
    REGISTRATION_SUCCESS(CREATED, "해당 강의가 성공적으로 신청되었습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    SuccessCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}
