package com.rafael.todolist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class User {

    @Getter
    @Setter
    @Id @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @Column(unique = true)

    private String username;
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    public User() {}


}
