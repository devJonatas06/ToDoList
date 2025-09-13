package com.project.todolist.TodoListBackend.dto;

import com.project.todolist.TodoListBackend.entity.Priority;
import com.project.todolist.TodoListBackend.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDateTime duration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO user;
    private List<SubTaskDTO> subtasks;
    private Set<LabelDTO> labels;

  
}
