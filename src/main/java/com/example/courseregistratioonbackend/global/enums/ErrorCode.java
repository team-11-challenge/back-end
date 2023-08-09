package com.example.courseregistratioonbackend.global.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    /* 400 BAD_REQUEST : 이 응답은 잘못된 문법으로 인해 서버가 요청을 이해할 수 없다는 의미입니다. */
    USER_LOGIN_FAILURE(BAD_REQUEST, "로그인 실패"),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    CREDIT_EXCEEDED(BAD_REQUEST, "이수 가능 학점이 초과되었습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않았다는 의미입니다. */
    INVALID_ADMIN_NUMBER(UNAUTHORIZED, "관리자 번호가 유효하지 않습니다."),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_USER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 403 FORBIDDEN : 클라이언트가 콘텐츠에 접근할 권리를 가지고 있지 않다는 의미입니다.*/
    NO_AUTHORITY_TO_DATA(FORBIDDEN, "해당 리소스에 대한 권한이 없습니다."),
    NO_AUTHORITY_TO_REGISTRATION(FORBIDDEN, "해당 수강신청을 한 사용자가 아닙니다."),

    /* 404 NOT_FOUND : 서버는 요청 받은 리소스를 찾을 수 없다는 의미입니다. */
    COURSE_NOT_FOUND(NOT_FOUND, "해당 강의를 찾을 수 없습니다."),
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    NOT_FOUND_CLIENT(NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
    NOT_FOUND_STUDENT(NOT_FOUND, "존재하지 않는 학생입니다."),
    NOT_FOUND_PERIOD(NOT_FOUND, "기간이 존재하지 않습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    COURSE_ALREADY_FULLED(CONFLICT, "수강 정원이 다 찼습니다."),
    SUBJECT_ALREADY_REGISTERED(CONFLICT, "이미 신청한 과목입니다."),
    COURSE_TIME_CONFLICT(CONFLICT, "이미 신청한 강의와 시간이 겹칩니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    ErrorCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}
