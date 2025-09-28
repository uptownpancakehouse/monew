package com.uphouse.monew.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ClientExceptionCode.INTERNAL_SERVER_ERROR,
            "예상치 못한 서버에러가 발생했습니다"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, ClientExceptionCode.INVALID_PARAMETER, "잘못된 인자입니다."),

    // USER
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, ClientExceptionCode.USER_NOT_FOUND, "유저가 존재하지 않습니다."),
    USER_INVALID_PASSWORD(HttpStatus.BAD_REQUEST, ClientExceptionCode.LOGIN_ERROR, "비밀번호가 일치하지 않습니다."),
    USER_EMAIL_ALREADY_USED(HttpStatus.CONFLICT, ClientExceptionCode.USER_EMAIL_ALREADY_USED,
        "이미 해당 이메일을 사용하는 유저가 존재합니다."),

    // COMMENT
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, ClientExceptionCode.COMMENT_NOT_FOUND, "댓글이 존재하지 않습니다.")

    ;

    private final HttpStatus httpStatus;
    private final ClientExceptionCode clientExceptionCode;
    private final String message;

    ExceptionCode(HttpStatus httpStatus, ClientExceptionCode clientExceptionCode, String message) {
        this.httpStatus = httpStatus;
        this.clientExceptionCode = clientExceptionCode;
        this.message = message;
    }
}
