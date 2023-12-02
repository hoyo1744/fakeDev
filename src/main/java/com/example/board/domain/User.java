package com.example.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "USERS")
@Getter
@ToString(of = {"id", "name"})
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    public void changeAuthorData(String name) {
        this.name = name;
    }

}
