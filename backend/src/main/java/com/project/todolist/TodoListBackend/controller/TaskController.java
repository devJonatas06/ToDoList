package com.project.todolist.TodoListBackend.controller;

import com.project.todolist.AuthBackEnd.infra.security.UserPrincipal;
import com.project.todolist.TodoListBackend.entity.Task;
import com.project.todolist.TodoListBackend.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks(@AuthenticationPrincipal UserPrincipal principal) {
        return taskService.getTasksByUser(principal.getUser());
    }

    @PostMapping
    public Task createTask(@AuthenticationPrincipal UserPrincipal principal,
                           @RequestBody Task task) {

        task.setUser(principal.getUser());
        return taskService.saveTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long id) {
        taskService.deleteTask(principal.getUser(), id);
    }
}
