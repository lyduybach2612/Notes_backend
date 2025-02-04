package com.bach.notes.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    NOTE_NOT_FOUND(1001,"Note Not Found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1002,"User Not Found", HttpStatus.NOT_FOUND),
    INVALID_VALIDATION(1003,"Invalid Validation", HttpStatus.BAD_REQUEST),
    NOT_EMPTY_VALIDATION(1004,"Title and content mustn't be empty", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1005,"Email Already Exists", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS(1006,"Username Already Exists", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1007,"Invalid Email", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1008,"Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1009,"Unauthorized", HttpStatus.UNAUTHORIZED),
    NOTE_NOT_BELONG(1009,"You don't have this note", HttpStatus.BAD_REQUEST),
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;

}
