package com.project.todolist.TodoListBackend.controller;

import com.project.todolist.AuthBackEnd.infra.security.UserPrincipal;
import com.project.todolist.TodoListBackend.dto.LabelDTO;
import com.project.todolist.TodoListBackend.dto.TaskDTO;
import com.project.todolist.TodoListBackend.dto.TaskMapper;
import com.project.todolist.TodoListBackend.dto.TaskRequestDTO;
import com.project.todolist.TodoListBackend.entity.Label;
import com.project.todolist.TodoListBackend.entity.Priority;
import com.project.todolist.TodoListBackend.entity.Task;
import com.project.todolist.TodoListBackend.repository.LabelRepository;
import com.project.todolist.TodoListBackend.repository.taskRepository;
import com.project.todolist.TodoListBackend.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getTasks(@AuthenticationPrincipal UserPrincipal principal) {
        return taskService.getTasksByUser(principal.getUser())
                .stream()
                .map(TaskMapper::toDTO)
                .toList();
    }


    @PostMapping

    public TaskDTO createTask(@AuthenticationPrincipal UserPrincipal principal,
                              @RequestBody TaskRequestDTO requestDTO) {

        // Buscar labels por ID se vierem no request
        Set<Label> labels = new HashSet<>();
        if (requestDTO.getLabelIds() != null && !requestDTO.getLabelIds().isEmpty()) {
            labels = taskService.findLabelsByIds(requestDTO.getLabelIds())
                    .stream().collect(Collectors.toSet());
        }

        Task task = taskService.fromRequestDTO(requestDTO, principal.getUser(), labels);
        Task savedTask = taskService.saveTask(task);

        return TaskMapper.toDTO(savedTask);
    }


    @GetMapping("/page")
    public Page<TaskDTO> getTasksPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return taskService.getAllTasks(PageRequest.of(page, size, Sort.by(sortBy).descending()))
                .map(TaskMapper::toDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long id) {
        taskService.deleteTask(principal.getUser(), id);
    }

    @GetMapping("/labels/{labelId}")
    public List<TaskDTO> getByLabel(@PathVariable Long labelId) {
        return taskService.findByLabel(labelId).stream()
                .map(TaskMapper::toDTO)
                .toList();
    }
    @GetMapping("/priority/{priority}")
    public List<TaskDTO> getByPriority(@PathVariable Priority priority) {
        return taskService.findByPriority(priority).stream()
                .map(TaskMapper::toDTO)
                .toList();
    }


}
