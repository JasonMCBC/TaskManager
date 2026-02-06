package com.example.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.model.Priority;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.Usuario;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Buscar por prioridad
    List<Task> findByPriority(Priority priority);

    // Buscar por estado (true = completada, false = pendiente)
    List<Task> findByCompleted(boolean completed);

    // Buscar por prioridad y estado a la vez
    List<Task> findByPriorityAndCompleted(Priority priority, Boolean completed);

    List<Task> findByUsuario(Usuario usuario);

    List<Task> findByUsuarioAndPriority(Usuario usuario, Priority priority);

    List<Task> findByUsuarioAndCompleted(Usuario usuario, Boolean completed);

    List<Task> findByUsuarioAndPriorityAndCompleted(
            Usuario usuario,
            Priority priority,
            Boolean completed
    );

}