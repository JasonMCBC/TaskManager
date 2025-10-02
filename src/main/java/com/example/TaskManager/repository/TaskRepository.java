package com.example.TaskManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TaskManager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Buscar por prioridad
    List<Task> findByPriority(String priority);

    // Buscar por estado (true = completada, false = pendiente)
    List<Task> findByCompleted(boolean completed);

    // Buscar por prioridad y estado a la vez
    List<Task> findByPriorityAndCompleted(String priority, boolean completed);
}