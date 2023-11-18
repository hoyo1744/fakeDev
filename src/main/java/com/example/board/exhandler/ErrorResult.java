package com.example.board.exhandler;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResult {

    private String code;

    private String message;
}
