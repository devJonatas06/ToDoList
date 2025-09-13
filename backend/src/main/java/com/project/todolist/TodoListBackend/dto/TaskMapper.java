package com.project.todolist.TodoListBackend.dto;

import com.project.todolist.AuthBackEnd.Domain.User;
import com.project.todolist.TodoListBackend.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TaskMapper {

    public static TaskDTO toDTO(Task task) {
        if (task == null) return null;

        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                toUserDTO(task.getUser()),
                toSubTaskDTOList(task.getSubtasks()),
                toLabelDTOSet(task.getLabels())
        );
    }

    private static UserDTO toUserDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    private static List<SubTaskDTO> toSubTaskDTOList(List<SubTask> subtasks) {
        if (subtasks == null) return List.of();
        return subtasks.stream()
                .map(subTask -> new SubTaskDTO(subTask.getId(), subTask.getTitle(), subTask.isCompleted()))
                .collect(Collectors.toList());
    }

    private static Set<LabelDTO> toLabelDTOSet(Set<Label> labels) {
        if (labels == null) return Set.of();
        return labels.stream()
                .map(label -> new LabelDTO(label.getId(), label.getName()))
                .collect(Collectors.toSet());
    }
}
