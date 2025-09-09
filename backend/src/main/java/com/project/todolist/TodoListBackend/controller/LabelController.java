package com.project.todolist.TodoListBackend.controller;

import com.project.todolist.AuthBackEnd.infra.security.UserPrincipal;
import com.project.todolist.TodoListBackend.dto.LabelDTO;
import com.project.todolist.TodoListBackend.entity.Label;
import com.project.todolist.TodoListBackend.repository.LabelRepository;
import com.project.todolist.TodoListBackend.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/labels")
public class LabelController {

    private final LabelRepository labelRepository;
    private final TaskService taskService;


    @PostMapping
    public Label createLabel(@RequestBody Label label) {
        return labelRepository.save(label);
    }

    @GetMapping
    public List<Label> getLabels() {
        return labelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Label getLabelById(@PathVariable Long id) {
        return labelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Label não encontrado"));
    }

    @PutMapping("/{id}")
    public Label updateLabel(@PathVariable Long id, @RequestBody Label labelDetails) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Label não encontrado"));

        label.setName(labelDetails.getName());
        return labelRepository.save(label);
    }

    @DeleteMapping("/{id}")
    public void deleteLabel(@PathVariable Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Label não encontrado"));

        labelRepository.delete(label);
    }

}
