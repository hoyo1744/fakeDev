package com.example.board.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostResponseDto {
    @NotEmpty
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private Long UserId;
}
