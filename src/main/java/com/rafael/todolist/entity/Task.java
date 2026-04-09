package com.rafael.todolist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private LocalDateTime date;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private TaskState state = TaskState.TO_DO; // estado inicial

    @Getter
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Task() {}


    public Task(long id, String name, String description, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    //for demo
    public Task(String name, String description, LocalDateTime date, TaskState taskState, Category categ) {
        this.id = 0L;
        this.name = name;
        this.description = description;
        this.date = date;
        this.state = taskState;
        this.category = categ;
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getTasks().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        user.getTasks().add(this);
    }
}
