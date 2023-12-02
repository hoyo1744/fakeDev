package com.example.board.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Getter
@ToString(of = {"id","title","content"})
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Post() {
    }

    public void changePostData(String title, String content) {
        this.title = title;
        this.content = content;
    }


    /**
     * 연관관계 편의 메서드(양방향 관계 모두 설정)
     */
    public void setAuthor(User user) {

        if (this.user != null) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
    }


}
