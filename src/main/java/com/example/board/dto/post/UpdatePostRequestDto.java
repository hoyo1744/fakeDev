package com.example.board.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdatePostRequestDto {
    @NotEmpty
    private final String title;

    @NotEmpty
    private final String content;
}
