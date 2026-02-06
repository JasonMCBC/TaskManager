package com.example.TaskManager.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.taskmanager.Services.TaskServiceImpl;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.mapper.TaskMapper;
import com.example.taskmanager.model.Priority;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.Usuario;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Usuario usuario;
    private Task task;
    private TaskDTO taskDTO;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        // Setup usuario
        usuario = Usuario.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .build();

        // Setup task
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setPriority(Priority.HIGH);
        task.setCompleted(false);
        task.setUsuario(usuario);

        // Setup TaskDTO
        taskDTO = new TaskDTO(
                1L,
                "Test Task",
                "Test Description",
                Priority.HIGH,
                false
        );

        // Setup TaskRequest
        taskRequest = new TaskRequest();
        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Test Description");
        taskRequest.setPriority(Priority.HIGH);
        taskRequest.setCompleted(false);

        // Setup Security Context
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("testuser");
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));
    }

    @Test
    void whenGetTaskById_thenReturnTaskDTO() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDTO(task)).thenReturn(taskDTO);

        // When
        TaskDTO result = taskService.getTaskById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void whenGetTaskById_withInvalidId_thenThrowException() {
        // Given
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTaskById(999L);
        });
    }

    @Test
    void whenCreateTask_thenReturnSavedTaskDTO() {
        // Given
        when(taskMapper.toEntity(taskRequest)).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDTO(task)).thenReturn(taskDTO);

        // When
        TaskDTO result = taskService.createTask(taskRequest);

        // Then
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void whenUpdateTask_thenReturnUpdatedTaskDTO() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDTO(task)).thenReturn(taskDTO);

        // When
        TaskDTO result = taskService.updateTask(1L, taskRequest);

        // Then
        assertNotNull(result);
        verify(taskMapper, times(1)).updateEntity(task, taskRequest);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void whenDeleteTask_thenSuccess() {
        // Given
        when(taskRepository.existsById(1L)).thenReturn(true);

        // When
        taskService.deleteTask(1L);

        // Then
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteTask_withInvalidId_thenThrowException() {
        // Given
        when(taskRepository.existsById(anyLong())).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.deleteTask(999L);
        });
    }
}