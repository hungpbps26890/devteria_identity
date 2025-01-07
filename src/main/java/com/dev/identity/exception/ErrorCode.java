package com.dev.identity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNEXPECTED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error"),
    USER_EXISTED(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "User already existed"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "User not found"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "Unauthenticated"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "Invalid token"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, "Do not have permission"),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "Role not found"),
    INVALID_DOB_AGE(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Age must be at least {min}"),
    INVALID_DOB_REQUIRED(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Date of birth is required"),
    INVALID_USERNAME_SIZE(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Username must be at least {min} characters"),
    INVALID_USERNAME_REQUIRED(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Username is required"),
    INVALID_PASSWORD_SIZE(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Password must be at least {min} characters"),
    INVALID_PASSWORD_REQUIRED(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Password is required");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}
