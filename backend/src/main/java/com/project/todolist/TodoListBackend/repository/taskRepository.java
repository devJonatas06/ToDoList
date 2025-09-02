package com.project.todolist.TodoListBackend.repository;

import com.project.todolist.AuthBackEnd.Domain.User;
import com.project.todolist.TodoListBackend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface taskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
