package com.example.board.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String name;
}
