package com.example.TaskManager.controller;

import java.util.List;

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
import com.example.TaskManager.repository.TaskRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping //GET Todas
    public List<TaskDTO> listar(){
        return taskRepository.findAll()
            .stream()
            .map(TaskMapper::toDTO)
            .toList();
    }

    // ✅ GET por id
    @GetMapping("/{id}")
    public TaskDTO obtener(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(TaskMapper::toDTO)
                .orElse(null);
    }

    // ✅ POST (crear)
    @PostMapping
    public TaskDTO crear(@Valid @RequestBody TaskRequest request) {
        Task tarea = TaskMapper.toEntity(request);
        return TaskMapper.toDTO(taskRepository.save(tarea));
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
                    return TaskMapper.toDTO(taskRepository.save(t));
                })
                .orElse(null);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

}
