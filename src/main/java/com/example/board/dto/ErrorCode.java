package com.example.board.dto;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();

    HttpStatus getHttpStatus();

    String getMessage();


}
