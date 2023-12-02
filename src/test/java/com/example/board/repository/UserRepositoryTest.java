package com.example.board.repository;

import com.example.board.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    @Test
    @DisplayName("save테스트")
    public void save() throws Exception {
        User user = new User();
        user.changeAuthorData("hoyong.eom");

        User saveAuthor = userRepository.save(user);

        Assertions.assertThat(saveAuthor.getName()).isEqualTo("hoyong.eom");
        Assertions.assertThat(saveAuthor.getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("findbyId테스트")
    public void findById() throws Exception {
        User user = new User();
        user.changeAuthorData("hoyong.eom");

        User saveAuthor = userRepository.save(user);

        User findAuthor = userRepository.findById(saveAuthor.getId()).get();

        Assertions.assertThat(findAuthor.getName()).isEqualTo(saveAuthor.getName());
    }

    @Test
    @DisplayName("findByName테스트")
    public void findByName() throws Exception {
        User user = new User();
        user.changeAuthorData("hoyong.eom");
        User saveUser = userRepository.save(user);

        List<User> users = userRepository.findByName(user.getName());

        Assertions.assertThat(users.isEmpty()).isFalse();
        Assertions.assertThat(users.get(0).getName()).isEqualTo(user.getName());
    }
}