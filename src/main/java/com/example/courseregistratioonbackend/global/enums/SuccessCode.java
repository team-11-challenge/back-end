package com.example.courseregistratioonbackend.global.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
public enum SuccessCode {
    /* 200 OK : 요청이 성공적으로 완료되었다는 의미입니다. */
    USER_LOGIN_SUCCESS(OK, "로그인 성공"),
//    USER_DATA_UPDATE_SUCCESS(OK, "유저 정보 변경 성공"),
//    EMAIL_SEND_SUCCESS(OK, "메일 전송 성공"),

    /* 201 CREATED : 요청이 성공적이었으며 그 결과로 새로운 리소스가 생성 되었다는 의미입니다. */
    USER_SIGNUP_SUCCESS(CREATED, "회원가입 성공")

    ;

    private final HttpStatus httpStatus;
    private final String detail;

    SuccessCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}
