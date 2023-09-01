package com.example.courseregistratioonbackend.global.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    /* 400 BAD_REQUEST : 이 응답은 잘못된 문법으로 인해 서버가 요청을 이해할 수 없다는 의미입니다. */
    COLLEGE_NAME_IS_REQUIRED(BAD_REQUEST, "대학 입력은 필수 입니다."),
    USER_LOGIN_FAILURE(BAD_REQUEST, "로그인 실패하였습니다."),
    CREDIT_EXCEEDED(BAD_REQUEST, "이수 가능 학점이 초과되었습니다."),
    JWT_PREFIX_ERROR(BAD_REQUEST, "인증 식별자 형식이 잘못되었습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않았다는 의미입니다. */
    UNAUTHORIZED_USER(UNAUTHORIZED, "인증에 실패하였습니다."),
    JWT_EXPIRATION(UNAUTHORIZED, "유효시간 만료, 재인증이 필요합니다."),

    /* 403 FORBIDDEN : 클라이언트가 콘텐츠에 접근할 권리를 가지고 있지 않다는 의미입니다.*/
    NO_AUTHORITY_TO_REGISTRATION(FORBIDDEN, "해당 수강신청을 한 사용자가 아닙니다."),
    NO_AUTHORITY_TO_BASKET(FORBIDDEN, "해당 수강과목을 장바구니에 담은 사용자가 아닙니다."),

    /* 404 NOT_FOUND : 서버는 요청 받은 리소스를 찾을 수 없다는 의미입니다. */
    COURSE_NOT_FOUND(NOT_FOUND, "해당 강의를 찾을 수 없습니다."),
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    STUDENT_NOT_FOUND(NOT_FOUND, "존재하지 않는 학생입니다."),
    PERIOD_NOT_FOUND(NOT_FOUND, "기간이 존재하지 않습니다."),
    BASKET_DATA_NOT_FOUND(NOT_FOUND, "해당 장바구니의 내역을 찾을 수 없습니다."),
    REGISTRATION_NOT_FOUND(NOT_FOUND, "해당 신청을 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    COURSE_ALREADY_FULLED(CONFLICT, "수강 정원이 다 찼습니다."),
    SUBJECT_ALREADY_REGISTERED(CONFLICT, "이미 신청한 과목입니다.\n(동일한 과목의 다른 분반을 다수 신청할 수 없습니다.)"),
    COURSE_TIME_CONFLICT(CONFLICT, "이미 신청한 강의와 시간이 겹칩니다."),
    ALREADY_BASKET_EXISTED(CONFLICT, "이미 장바구니에 담은 과목입니다."),
    EXISTED_CACHE_EXCEPTION(CONFLICT, "캐시에 이미 있는 과목입니다.");

    private final HttpStatus httpStatus;
    private final String detail;

    ErrorCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}