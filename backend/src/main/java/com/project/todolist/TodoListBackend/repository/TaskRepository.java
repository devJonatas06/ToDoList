package com.project.todolist.TodoListBackend.repository;

import com.project.todolist.AuthBackEnd.Domain.User;
import com.project.todolist.TodoListBackend.entity.Priority;
import com.project.todolist.TodoListBackend.entity.Status;
import com.project.todolist.TodoListBackend.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    Page<Task> findAll(Pageable pageable);
    List<Task> findByUser(User user);
    List<Task> findByPriority(Priority priority);
    List<Task> findByStatus(Status status );
    List<Task> findByDueDateBetween(LocalDateTime start, LocalDateTime end);
    Page<Task> findByUser(User user, Pageable pageable);
    List<Task> findByLabels_Id(Long labelId);


}
