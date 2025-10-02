package com.example.TaskManager.mapper;

import com.example.TaskManager.dto.TaskDTO;
import com.example.TaskManager.dto.TaskRequest;
import com.example.TaskManager.model.Task;

public class TaskMapper {
    public static TaskDTO toDTO(Task tarea){
        return new TaskDTO(
            tarea.getId(),
            tarea.getTitle(),
            tarea.getDescription(),
            tarea.getPriority(),
            tarea.isCompleted()
        );
    }

    public static Task toEntity(TaskRequest request){
        return new Task(
            request.getTitle(),
            request.getDescription(),
            request.getPriority(),
            request.isCompleted()
        );
    }

    public static void updateEntity(Task task, TaskRequest request){
        
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setCompleted(request.isCompleted());
    }
}
