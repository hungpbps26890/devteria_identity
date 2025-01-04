package com.dev.identity.exception;

import com.dev.identity.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
        log.error("Exception: {}", exception.toString());

        ErrorCode errorCode = ErrorCode.UNEXPECTED_EXCEPTION;

        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .build());
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }
}
