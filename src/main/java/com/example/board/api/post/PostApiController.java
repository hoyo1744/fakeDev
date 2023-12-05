package com.example.board.api.post;

import com.example.board.domain.Post;
import com.example.board.dto.ErrorResponse;
import com.example.board.dto.post.*;
import com.example.board.exception.ResponseEntityFactory;
import com.example.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ErrorResponse<CreatePostResponseDto>> savePost(@RequestBody @Valid CreatePostRequestDto requestSavePost) {
        Long savePostId = postService.save(requestSavePost.getTitle(), requestSavePost.getContent(), requestSavePost.getUserId());
        return ResponseEntityFactory.success(new CreatePostResponseDto(savePostId, requestSavePost.getTitle(), requestSavePost.getContent(), requestSavePost.getUserId()));
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/api/post/{id}")
    public ResponseEntity<ErrorResponse<UpdatePostResponseDto>> updatePost(@PathVariable Long id, @RequestBody @Valid UpdatePostRequestDto requestUpdatePost) {
        Long updateId = postService.updateOne(id, requestUpdatePost.getTitle(), requestUpdatePost.getContent());
        return ResponseEntityFactory.success(new UpdatePostResponseDto(updateId, requestUpdatePost.getTitle(), requestUpdatePost.getContent()));
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/api/post/{id}")
    public ResponseEntity<ErrorResponse<PostDto>> getPost(@PathVariable("id") Long id) {
        Post findPost = postService.findOne(id).get();
        return ResponseEntityFactory.success(new PostDto(findPost.getId(), findPost.getTitle(), findPost.getContent(), findPost.getUser().getName()));
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/api/post/{id}")
    public ResponseEntity<ErrorResponse<DeletePostResponseDto>> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return ResponseEntityFactory.success(new DeletePostResponseDto(id));
    }

    /**
     * 전체 게시글 조회
     */
    @GetMapping("/api/posts")
    public ResponseEntity<ErrorResponse<List<PostDto>>> getPosts() {
        List<Post> posts = postService.findAll();
        List<PostDto> collect = posts.stream().map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getUser().getName())).collect(toList());
        return ResponseEntityFactory.success(collect);
    }
}
