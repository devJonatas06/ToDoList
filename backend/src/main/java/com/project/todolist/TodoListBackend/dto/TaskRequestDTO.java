package com.project.todolist.TodoListBackend.dto;

import com.project.todolist.TodoListBackend.entity.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {
    private String title;
    private String description;
    private Priority priority;
    private boolean completed;
    private LocalDateTime dateTime;
    private List<SubTaskDTO> subtasks;
    private Set<Long> labelIds; // apenas IDs das labels já existentes
}
