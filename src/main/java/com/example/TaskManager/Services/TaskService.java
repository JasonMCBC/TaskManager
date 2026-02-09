package com.example.taskmanager.Services;

import java.util.List;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.model.Priority;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    TaskDTO getTaskById(Long id);

    TaskDTO   createTask(TaskRequest request);

    TaskDTO updateTask(Long id, TaskRequest request);

    void deleteTask(Long id);

    List<TaskDTO> getTasks(Priority priority, Boolean completed);

    Page<TaskDTO> getTasksPaginated(Priority priority, Boolean completed, Pageable pageable);

}
