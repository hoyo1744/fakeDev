package com.example.board.api.post;

import com.example.board.domain.Post;
import com.example.board.dto.post.CreatePostRequestDto;
import com.example.board.exception.UserException;
import com.example.board.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.*;

@Controller
@RestController
@RequiredArgsConstructor
@Slf4j
public class PostApiController {

    private final PostService postService;


    /**
     * 게시글 저장
     *
     * @return
     */
    @PostMapping("/api/post")
    public CreatePostResponseDto savePost(@RequestBody @Valid CreatePostRequestDto requestSavePost) {

        Post post = new Post(requestSavePost.getTitle(), requestSavePost.getContent());
        Long savePostId = postService.save(post);
        return new CreatePostResponseDto(savePostId, post.getTitle(), post.getContent());
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/api/post/{id}")
    public UpdatePostResponseDto updatePost(@PathVariable Long id, @RequestBody @Valid UpdatePostRequestDto requestUpdatePost) {
        Long updateId = postService.update(id, requestUpdatePost.getTitle(), requestUpdatePost.getContent());
        return new UpdatePostResponseDto(updateId, requestUpdatePost.getTitle(), requestUpdatePost.getContent());
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/api/post/{id}")
    public PostDto getPost(@PathVariable("id") Long id) {
        Post findPost = postService.findOne(id).get();
        return new PostDto(findPost.getId(), findPost.getTitle(), findPost.getContent());
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/api/post/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
    }

    /**
     * 전체 게시글 조회
     */
    @GetMapping("/api/posts")
    public List<PostDto> getPosts() {
        List<Post> posts = postService.findAll();
        return posts.stream().map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent())).collect(toList());
    }


    /**
     * 에러 체크용
     */
    @Profile(value = "test")
    @GetMapping("/api/exception/{id}")
    public PostDto getException(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new PostDto(Long.parseLong(id), "title", "content");
    }


    @Data
    @AllArgsConstructor
    static class PostDto {
        private Long id;
        private String title;
        private String content;
    }




    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UpdatePostRequestDto {
        @NotBlank
        private String title;

        @NotBlank
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class CreatePostResponseDto {
        private Long id;
        @NotBlank
        private String title;

        @NotBlank
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class UpdatePostResponseDto {
        private Long id;

        @NotBlank
        private String title;

        @NotBlank
        private String content;
    }

}
