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
import org.springframework.web.bind.annotation.RestController;

import com.example.TaskManager.dto.TaskDTO;
import com.example.TaskManager.dto.TaskRequest;
import com.example.TaskManager.mapper.TaskMapper;
import com.example.TaskManager.model.Task;
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

    @GetMapping //GET Todas
    public List<TaskDTO> listar(){
        return taskRepository.findAll()
            .stream()
            .map(taskMapper::toDTO)
            .toList();
    }

    // ✅ GET por id
    @GetMapping("/{id}")
    public TaskDTO obtener(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDTO)
                .orElse(null);
    }
    
    // ✅ GET por Priority
    @GetMapping("/Priority/{priority}")
    public List<TaskDTO>  obtener(@PathVariable Priority priority) {
        return taskRepository.findByPriority(priority)
            .stream()
            .map(taskMapper::toDTO)
            .collect(Collectors.toList());
    }
    // ✅ GET por Complete
    @GetMapping("/Completed/{completed}")
    public List<TaskDTO>  obtener(@PathVariable boolean completed) {
        return taskRepository.findByCompleted(completed)
            .stream()
            .map(taskMapper::toDTO)
            .collect(Collectors.toList());
    }
    // ✅ GET por Complete + Priority
    @GetMapping("/Priority/{priority}/Completed/{completed}")
    public List<TaskDTO>  obtener(@PathVariable Priority priority, @PathVariable boolean completed) {
        return taskRepository.findByPriorityAndCompleted(priority, completed)
            .stream()
            .map(taskMapper::toDTO)
            .collect(Collectors.toList());
    }

    // ✅ POST (crear)
    @PostMapping
    public TaskDTO crear(@Valid @RequestBody TaskRequest request) {
        Task task = taskMapper.toEntity(request);
        return taskMapper.toDTO(taskRepository.save(task));
    }

    // ✅ PUT (actualizar)
    @PutMapping("/{id}")
    public TaskDTO actualizar(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return taskRepository.findById(id)
                .map(t -> {
                    t.setTitle(request.getTitle());
                    t.setDescription(request.getDescription());
                    t.setPriority(request.getPriority());
                    t.setCompleted(request.isCompleted());
                    return taskMapper.toDTO(taskRepository.save(t));
                })
                .orElse(null);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

}
