package com.project.todolist.TodoListBackend.controller;

import com.project.todolist.TodoListBackend.entity.Label;
import com.project.todolist.TodoListBackend.repository.LabelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/labels")
public class LabelController {

    private final LabelRepository labelRepository;

    public LabelController(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @PostMapping
    public Label createLabel(@RequestBody Label label) {
        return labelRepository.save(label);
    }

    @GetMapping
    public List<Label> getLabels() {
        return labelRepository.findAll();
    }
}
