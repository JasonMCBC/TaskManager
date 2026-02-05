package com.example.TaskManager.Services;

import java.util.List;

import com.example.TaskManager.dto.TaskDTO;
import com.example.TaskManager.dto.TaskRequest;
import com.example.TaskManager.model.Priority;

public interface TaskService {

    TaskDTO getTaskById(Long id);

    TaskDTO   createTask(TaskRequest request);

    TaskDTO updateTask(Long id, TaskRequest request);

    void deleteTask(Long id);

    List<TaskDTO> getTasks(Priority priority, Boolean completed);

}
