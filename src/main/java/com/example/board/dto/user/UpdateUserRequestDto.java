package com.example.board.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UpdateUserRequestDto {

    @NotEmpty
    private final Long Id;

    @NotEmpty
    private final String name;
}
