package com.project.todolist.TodoListBackend.service;

import com.project.todolist.AuthBackEnd.Domain.User;
import com.project.todolist.TodoListBackend.dto.LabelDTO;
import com.project.todolist.TodoListBackend.dto.TaskRequestDTO;
import com.project.todolist.TodoListBackend.entity.Label;
import com.project.todolist.TodoListBackend.entity.Priority;
import com.project.todolist.TodoListBackend.entity.SubTask;
import com.project.todolist.TodoListBackend.entity.Task;
import com.project.todolist.TodoListBackend.repository.LabelRepository;
import com.project.todolist.TodoListBackend.repository.taskRepository;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final taskRepository taskRepository;
    private final LabelRepository labelRepository;

    public TaskService(taskRepository taskRepository, LabelRepository labelRepository) {
        this.taskRepository = taskRepository;
        this.labelRepository = labelRepository;
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public void deleteTask(User user, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (Objects.equals(task.getUser().getId(), user.getId())) {
            taskRepository.delete(task);
        } else {
            throw new RuntimeException("Cannot delete task of another user");
        }
    }
    public void deleteLabel(LabelDTO labelDTO, Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Label not found"));
        if (Objects.equals(label.getName(), label.getId())) {
            labelRepository.delete(label);
        } else {
            throw new RuntimeException("Cannot delete label or not exist");
        }
    }

    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Task saveTask(Task task) {
        if (task.getSubtasks() != null) {
            task.getSubtasks().forEach(subtask -> subtask.setTask(task));
        }
        return taskRepository.save(task);
    }
    public Set<Label> findLabelsByIds(Set<Long> labelIds) {
        if (labelIds == null || labelIds.isEmpty()) {
            return Set.of();
        }
        return labelRepository.findByIdIn(labelIds);
    }



    public Task fromRequestDTO(TaskRequestDTO dto, User user, Set<Label> labels) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setCompleted(dto.isCompleted());
        task.setUser(user);

        if (dto.getSubtasks() != null) {
            task.setSubtasks(dto.getSubtasks().stream()
                    .map(sub -> {
                        SubTask st = new SubTask();
                        st.setTitle(sub.getTitle());
                        st.setCompleted(sub.isCompleted());
                        st.setTask(task);
                        return st;
                    }).collect(Collectors.toList()));
        }

        if (labels != null) {
            task.setLabels(labels);
        }

        return task;
    }
    public List<Task> findByLabel(Long labelId) {
        return taskRepository.findByLabels_Id(labelId);
    }
    public List<Task> findByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }

}
