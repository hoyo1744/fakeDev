package com.example.board.exception;

import com.example.board.dto.ErrorResponse;
import com.example.board.dto.post.CreatePostResponseDto;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFactory<T> {
    public static <T> ResponseEntity<ErrorResponse<T>> success(T data) {
        ErrorResponse<T> response = ErrorResponse.success(data);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
