package com.project.todolist.TodoListBackend.service;

import com.project.todolist.AuthBackEnd.Domain.User;
import com.project.todolist.TodoListBackend.entity.Task;
import com.project.todolist.TodoListBackend.repository.taskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final taskRepository taskRepository;

    public TaskService(taskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(User user, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if(task.getUser().getId().equals(user.getId())){
            taskRepository.delete(task);
        } else {
            throw new RuntimeException("Cannot delete task of another user");
        }
    }
}
