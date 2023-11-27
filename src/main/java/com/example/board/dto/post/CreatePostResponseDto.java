package com.example.board.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostResponseDto {
    private Long id;
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
