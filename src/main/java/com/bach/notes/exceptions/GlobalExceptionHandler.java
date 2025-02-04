package com.bach.notes.exceptions;

import com.bach.notes.dtos.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {

        ApiResponse<String> apiResponse = new ApiResponse<>();
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.code);
        return ResponseEntity
                .status(errorCode.httpStatusCode)
                .body(apiResponse);

    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<String>> handleAppException(AppException e) {
        ApiResponse<String> response = new ApiResponse<>();
        ErrorCode errorCode = e.getErrorCode();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponse<String> response = new ApiResponse<>();
        ErrorCode errorCode = null;
        try {
            String message = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
            errorCode = ErrorCode.valueOf(message);
            response.setCode(errorCode.getCode());
            response.setMessage(errorCode.getMessage());
        }catch (Exception ex) {
            errorCode = ErrorCode.INVALID_VALIDATION;
            response.setCode(errorCode.getCode());
            response.setMessage(errorCode.getMessage());
        }
        return ResponseEntity
                .status(errorCode.httpStatusCode)
                .body(response);
    }

    @ExceptionHandler(value = AuthenticationServiceException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationServiceException(AuthenticationServiceException e) {
        ApiResponse<String> response = new ApiResponse<>();
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.httpStatusCode)
                .body(response);
    }

}
