package com.example.taskmanager.Services;

import java.util.List;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.model.Priority;

public interface TaskService {

    TaskDTO getTaskById(Long id);

    TaskDTO   createTask(TaskRequest request);

    TaskDTO updateTask(Long id, TaskRequest request);

    void deleteTask(Long id);

    List<TaskDTO> getTasks(Priority priority, Boolean completed);

}
