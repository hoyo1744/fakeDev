package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.exception.RestApiException;
import com.example.board.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("join테스트")
    public void join() throws Exception {
        Long joinId = userService.join("hoyong.eom");

        Assertions.assertThat(joinId).isNotZero();
    }

    @Test
    @DisplayName("중복 회원가입 불가 테스트")
    public void duplicatedJoin() throws Exception {
        //given
        Long joinId = userService.join("hoyong.eom");

        User findUser = userService.findOne(joinId);
        Assertions.assertThat(findUser.getName()).isEqualTo("hoyong.eom");

        Assertions.assertThatThrownBy(() -> userService.join("hoyong.eom")).isInstanceOf(RestApiException.class);
    }
}