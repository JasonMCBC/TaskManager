package com.example.taskmanager.mapper;

import org.springframework.stereotype.Component;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.model.Task;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        if (task == null) return null;

        return new TaskDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getPriority(),
            task.isCompleted()
        );
    }

    public Task toEntity(TaskRequest request) {
        if (request == null) return null;

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setCompleted(request.isCompleted());

        return task;
    }

    public void updateEntity(Task task, TaskRequest request){
        if (request == null) return;

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setCompleted(request.isCompleted());
    }
}