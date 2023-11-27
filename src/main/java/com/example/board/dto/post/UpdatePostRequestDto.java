package com.example.board.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdatePostRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
