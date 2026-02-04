package com.example.TaskManager.Services;

import java.util.List;

import com.example.TaskManager.dto.TaskDTO;
import com.example.TaskManager.dto.TaskRequest;
import com.example.TaskManager.model.Priority;

public interface TaskService {

    List<TaskDTO> getAllTasks();

    TaskDTO getTaskById(Long id);

    TaskDTO   createTask(TaskRequest request);

    TaskDTO updateTask(Long id, TaskRequest request);

    void deleteTask(Long id);

    List<TaskDTO> getTasksByPriority(Priority priority);

    List<TaskDTO> getTasksByCompleted(Boolean completed);

    List<TaskDTO> getTasks(Priority priority, Boolean completed);

}
