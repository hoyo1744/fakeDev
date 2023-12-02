package com.example.board.repository;

import com.example.board.domain.User;
import com.example.board.domain.Post;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository authorRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("PostRepository save 기본 테스트")
    public void save() throws Exception {
        Post post = new Post();
        post.changePostData("title", "content");
        Post save = postRepository.save(post);
        Assertions.assertThat(post.getId()).isEqualTo(save.getId());
    }


    @Test
    @DisplayName("PostRepository find 기본 테스트")
    public void findById() throws Exception {
        Post post = new Post();
        post.changePostData("title", "content");
        Post save = postRepository.save(post);

        em.flush();
        em.clear();

        Optional<Post> findPost = postRepository.findById(post.getId());

        Assertions.assertThat(post.getId()).isEqualTo(findPost.get().getId());

    }


    @Test
    @DisplayName("PostRepository 연관관계 매핑 테스트")
    public void findByIdRelationShipTest() throws Exception {
        Post post = new Post();
        post.changePostData("title", "content");

        User author = new User();
        author.changeAuthorData("hoyong.eom");
        User saveAuthor = authorRepository.save(author);

        post.setAuthor(saveAuthor);
        Post savePost = postRepository.save(post);

        Assertions.assertThat(savePost.getId()).isEqualTo(post.getId());
        Assertions.assertThat(saveAuthor.getId()).isEqualTo(savePost.getUser().getId());
        Assertions.assertThat(saveAuthor.getName()).isEqualTo(savePost.getUser().getName());
    }
}