package com.dev.identity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNEXPECTED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error"),
    USER_EXISTED(HttpStatus.BAD_REQUEST.value(), "User already existed"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "User not found"),
    UNAUTHENTICATED(HttpStatus.BAD_REQUEST.value(), "Username or password is incorrect"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "Do not have permission to access");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
