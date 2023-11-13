package com.example.board.service;

import com.example.board.domain.Post;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    /**
     * 포스팅 저장
     */
    @Transactional
    public Long save(Post post) {
        Post save = postRepository.save(post);
        return save.getId();
    }

    /**
     * 포스팅 수정
     */
    @Transactional
    public Long update(Long postId, String title, String content) {
        Post findPost = postRepository.findById(postId).get();
        findPost.changePostData(title, content);
        return findPost.getId();
    }

    /**
     * 포스팅 조회
     */
    public Optional<Post> findOne(Long postId) {
        return postRepository.findById(postId);
    }

    /**
     * 전체 포스팅 조회
     */
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * 포스팅 삭제
     */
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
