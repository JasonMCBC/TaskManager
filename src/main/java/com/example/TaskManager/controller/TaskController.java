package com.example.TaskManager.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.TaskManager.Services.TaskService;
import com.example.TaskManager.dto.TaskDTO;
import com.example.TaskManager.dto.TaskRequest;
import com.example.TaskManager.mapper.TaskMapper;
import com.example.TaskManager.model.Priority;
import com.example.TaskManager.repository.TaskRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskService taskService;

    // ✅ GET por Priority, Complete, ambos o ninguno
    @GetMapping
    public List<TaskDTO> getTasks(
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Boolean completed
    ) {
        return taskService.getTasks(priority, completed);
    }

    // ✅ GET por id
    @GetMapping("/{id}")
    public TaskDTO getById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // ✅ POST (crear)
    @PostMapping
    public TaskDTO create(@Valid @RequestBody TaskRequest request) {
        return taskService.createTask(request);
    }

    // ✅ PUT (actualizar)
    @PutMapping("/{id}")
    public TaskDTO update(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request
    ) {
        return taskService.updateTask(id, request);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
