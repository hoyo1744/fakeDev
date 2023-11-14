package com.example.board.api.post;

import com.example.board.domain.Post;
import com.example.board.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Controller
@RestController
@RequiredArgsConstructor
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
    static class CreatePostRequestDto {
        @NotEmpty
        @NotNull
        private String title;

        @NotEmpty
        @NotNull
        private String content;




    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UpdatePostRequestDto {
        @NotEmpty
        @NotNull
        private String title;

        @NotEmpty
        @NotNull
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class CreatePostResponseDto {
        private Long id;
        @NotEmpty
        @NotNull
        private String title;

        @NotEmpty
        @NotNull
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class UpdatePostResponseDto {
        private Long id;

        @NotEmpty
        @NotNull
        private String title;

        @NotEmpty
        @NotNull
        private String content;
    }

}
