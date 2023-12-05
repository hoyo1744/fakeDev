package com.example.board.api.user;

import com.example.board.domain.User;
import com.example.board.dto.ErrorResponse;
import com.example.board.dto.SuccessCode;
import com.example.board.dto.UserErrorCode;
import com.example.board.dto.user.*;
import com.example.board.exception.ResponseEntityFactory;
import com.example.board.service.UserService;
import com.sun.net.httpserver.Authenticator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//        TODO : 이거 팩토리로 어떻게 할 수 있나 고민할 필요가 있겠군. 중복된 코드가 너무 많아져
//        줄이긴 했는데 아직도 귀찮군..
//        ResponseEntity를 만드는것도 중복된다. 사실 여기에 Dto만 전달하니까..
//        회사에서는 어떤 방식으로 코드가 작성되어있을까?

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;


    /**
     * 회원 조회
     */
    @GetMapping("/api/user/{id}")
    public ResponseEntity<ErrorResponse<UserDto>> getUser(@PathVariable("id") Long id) {
        User findUser = userService.findOne(id);
        return ResponseEntityFactory.success(new UserDto(findUser.getId(), findUser.getName()));
    }


    /**
     * 회원 가입
     */
    @PostMapping("/api/user")
    public ResponseEntity<ErrorResponse<CreateUserResponseDto>> joinUser(@RequestBody @Valid CreateUserRequestDto createJoinUser) {
        Long joinId = userService.join(createJoinUser.getName());
        return ResponseEntityFactory.success(new CreateUserResponseDto(joinId, createJoinUser.getName()));
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<ErrorResponse<DeleteUserResponseDto>> DeleteUser(@PathVariable("id") Long id) {
        userService.deleteOne(id);
        return ResponseEntityFactory.success(new DeleteUserResponseDto(id));
    }

    /**
     * 회원 수정
     */
    @PutMapping("/api/user")
    public ResponseEntity<ErrorResponse<UpdateUserResponseDto>> updateUser(@RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        userService.updateOne(updateUserRequestDto.getId(), updateUserRequestDto.getName());
        return ResponseEntityFactory.success(new UpdateUserResponseDto(updateUserRequestDto.getName()));
    }

}
