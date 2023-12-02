package com.example.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{

    DUPLICATED_USER(HttpStatus.BAD_REQUEST, "duplicated user"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
