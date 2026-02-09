package com.example.taskmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.taskmanager.Services.TaskService;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.model.Priority;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ========================================
    // ENDPOINT NUEVO: GET con paginación
    // /tasks/paginated?page=0&size=20
    // ========================================
    @GetMapping("/paginated")
    public ResponseEntity<Page<TaskDTO>> getTasksPaginated(
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") 
            ? Sort.Direction.ASC 
            : Sort.Direction.DESC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        Page<TaskDTO> tasks = taskService.getTasksPaginated(priority, completed, pageable);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Obtener todas las tareas del usuario autenticado
     * Opcionalmente filtrar por prioridad y/o estado de completado
     */

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Boolean completed
    ) {
        List<TaskDTO> tasks = taskService.getTasks(priority, completed);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Obtener una tarea específica por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable Long id) {
        TaskDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Crear una nueva tarea
     */
    @PostMapping
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskRequest request) {
        TaskDTO createdTask = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    /**
     * Actualizar una tarea existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request
    ) {
        TaskDTO updatedTask = taskService.updateTask(id, request);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Eliminar una tarea
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}