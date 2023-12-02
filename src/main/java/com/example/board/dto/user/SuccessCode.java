package com.example.board.dto.user;

import com.example.board.dto.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ErrorCode {

    SUCCESS(HttpStatus.OK, "success"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
