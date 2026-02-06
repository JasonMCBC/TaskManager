package com.example.taskmanager.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.mapper.TaskMapper;
import com.example.taskmanager.model.Priority;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UsuarioRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long id) {
        log.debug("Buscando tarea con id: {}", id);
        
        return taskRepository.findById(id)
            .map(taskMapper::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));
    }
    
    @Override
    @Transactional
    public TaskDTO createTask(TaskRequest request) {
        Usuario usuario = getUsuarioAutenticado();
        log.info("Creando nueva tarea para usuario: {}", usuario.getUsername());

        Task task = taskMapper.toEntity(request);
        task.setUsuario(usuario);

        Task savedTask = taskRepository.save(task);
        log.info("Tarea creada exitosamente con id: {}", savedTask.getId());
        
        return taskMapper.toDTO(savedTask);
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long id, TaskRequest request) {
        log.debug("Actualizando tarea con id: {}", id);
        
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));

        taskMapper.updateEntity(task, request);

        Task updatedTask = taskRepository.save(task);
        log.info("Tarea actualizada exitosamente: {}", id);
        
        return taskMapper.toDTO(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        log.debug("Eliminando tarea con id: {}", id);
        
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarea no encontrada con id: " + id);
        }
        
        taskRepository.deleteById(id);
        log.info("Tarea eliminada exitosamente: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasks(Priority priority, Boolean completed) {
        Usuario usuario = getUsuarioAutenticado();
        log.debug("Obteniendo tareas para usuario: {} (priority={}, completed={})", 
                  usuario.getUsername(), priority, completed);

        List<Task> tasks;

        if (priority != null && completed != null) {
            tasks = taskRepository.findByUsuarioAndPriorityAndCompleted(usuario, priority, completed);
        } else if (priority != null) {
            tasks = taskRepository.findByUsuarioAndPriority(usuario, priority);
        } else if (completed != null) {
            tasks = taskRepository.findByUsuarioAndCompleted(usuario, completed);
        } else {
            tasks = taskRepository.findByUsuario(usuario);
        }

        log.debug("Se encontraron {} tareas", tasks.size());
        
        return tasks.stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    /**
     * Obtiene el usuario autenticado desde el contexto de seguridad
     */
    private Usuario getUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        log.debug("Obteniendo usuario autenticado: {}", username);
        
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
    }
}