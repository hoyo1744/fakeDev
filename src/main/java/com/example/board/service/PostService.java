package com.example.board.service;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
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
    private final UserRepository userRepository;

    /**
     * 포스팅 저장
     */
    @Transactional
    public Long save(String title, String content, Long userId) {
        Post post = new Post();
        post.changePostData(title, content);

        User findUser = userRepository.findById(userId).get();

        post.setAuthor(findUser);

        return postRepository.save(post).getId();
    }

    /**
     * 포스팅 수정
     */
    @Transactional
    public Long updateOne(Long postId, String title, String content) {
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
    public Long deletePost(Long postId) {
        postRepository.deleteById(postId);
        return postId;
    }
}
