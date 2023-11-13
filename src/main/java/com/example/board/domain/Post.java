package com.example.board.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;
    private String title;
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public void changePostData(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
