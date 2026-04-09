package com.rafael.todolist.repository;

import com.rafael.todolist.entity.Category;
import com.rafael.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
