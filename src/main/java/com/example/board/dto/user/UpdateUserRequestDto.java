package com.example.board.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserRequestDto {

    @NotEmpty
    private final Long Id;

    @NotEmpty
    private final String name;
}
