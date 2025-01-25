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
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;

}
