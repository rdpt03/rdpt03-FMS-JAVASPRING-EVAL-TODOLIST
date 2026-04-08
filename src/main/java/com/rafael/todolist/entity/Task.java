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


    public Task(long id, String name, String description, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getTasks().add(this);
    }
}
