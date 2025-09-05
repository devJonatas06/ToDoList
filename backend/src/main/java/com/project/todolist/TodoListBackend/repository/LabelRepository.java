package com.project.todolist.TodoListBackend.repository;

import com.project.todolist.TodoListBackend.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Set<Label> findByIdIn(Set<Long> ids);
}