package com.dev.identity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNEXPECTED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error"),
    USER_EXISTED(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "User already existed"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "User not found"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "Unauthenticated"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, "Do not have permission");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}
