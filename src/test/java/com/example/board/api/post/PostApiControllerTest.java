package com.example.board.api.post;

import com.example.board.domain.User;
import com.example.board.dto.post.*;
import com.example.board.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class PostApiControllerTest {

    @Autowired
    PostApiController postApiController;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        User user = new User();
        user.changeAuthorData("hoyong.eom");

        userRepository.save(user);
    }

    @Test
    @DisplayName("게시글 저장 테스트")
    public void savePost() throws Exception {
        //given
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("title", "content", 1L);

        //when
        CreatePostResponseDto createPostResponseDto = postApiController.savePost(createPostRequestDto);

        //then
        Assertions.assertThat(createPostResponseDto.getTitle()).isEqualTo(createPostRequestDto.getTitle());
        Assertions.assertThat(createPostResponseDto.getContent()).isEqualTo(createPostRequestDto.getContent());
        Assertions.assertThat(createPostResponseDto.getId()).isNotZero();
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updatePost() throws Exception {
        //given
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("title", "content", 1L);
        CreatePostResponseDto createPostResponseDto = postApiController.savePost(createPostRequestDto);

        //when
        UpdatePostRequestDto updatePostRequestDto = new UpdatePostRequestDto("title2", "content2");
        UpdatePostResponseDto updatePostResponseDto = postApiController.updatePost(createPostResponseDto.getId(), updatePostRequestDto);

        //then
        Assertions.assertThat(updatePostResponseDto.getId()).isEqualTo(createPostResponseDto.getId());
        Assertions.assertThat(updatePostResponseDto.getTitle()).isEqualTo("title2");
        Assertions.assertThat(updatePostResponseDto.getContent()).isEqualTo("content2");
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    public void getPost() throws Exception {
        //given
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("title", "content", 1L);
        CreatePostResponseDto createPostResponseDto = postApiController.savePost(createPostRequestDto);

        //when
        PostDto findPost = postApiController.getPost(createPostResponseDto.getId());

        //then
        Assertions.assertThat(findPost.getId()).isEqualTo(createPostResponseDto.getId());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void delete() throws Exception {
        //given
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("title", "content", 1L);
        CreatePostResponseDto createPostResponseDto = postApiController.savePost(createPostRequestDto);

        //when
        postApiController.deletePost(createPostResponseDto.getId());

        //then
        List<PostDto> posts = postApiController.getPosts();
        Assertions.assertThat(posts.size()).isEqualTo(0);
    }


}