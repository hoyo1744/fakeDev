package com.example.board.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
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
