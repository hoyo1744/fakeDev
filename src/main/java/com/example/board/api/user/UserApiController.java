package com.example.board.api.user;

import com.example.board.domain.User;
import com.example.board.dto.user.*;
import com.example.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;



    /**
     * 회원 조회
     */
    @GetMapping("/api/user/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        User findUser = userService.findOne(id);
        return new UserDto(findUser.getId(), findUser.getName());
    }


    /**
     * 회원 가입
     */
    @PostMapping("/api/user")
    public CreateUserResponseDto joinUser(@RequestBody @Valid CreateUserRequestDto createJoinUser) {
        userService.join(createJoinUser.getName());
        return new CreateUserResponseDto(createJoinUser.getName());
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping("/api/user/{id}")
    public void DeleteUser(@PathVariable("id") Long id) {
        userService.deleteOne(id);
    }

    /**
     * 회원 수정
     */
    @PutMapping("/api/user")
    public UpdateUserResponseDto updateUser(@RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        userService.updateOne(updateUserRequestDto.getId(), updateUserRequestDto.getName());
        return new UpdateUserResponseDto(updateUserRequestDto.getName());
    }

}
