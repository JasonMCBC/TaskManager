package com.example.TaskManager.Services;

import com.example.TaskManager.dto.TaskDTO;
import com.example.TaskManager.dto.TaskRequest;
import com.example.TaskManager.exception.ResourceNotFoundException;
import com.example.TaskManager.mapper.TaskMapper;
import com.example.TaskManager.model.Priority;
import com.example.TaskManager.model.Task;
import com.example.TaskManager.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskDTO> getAllTasks(){
        return taskRepository.findAll()
        .stream()
        .map(taskMapper::toDTO)
        .toList();
    }

    @Override
    public TaskDTO getTaskById(Long id){
        return taskRepository.findById(id)
            .map(taskMapper::toDTO)
            .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
    }
    @Override
    public TaskDTO createTask(TaskRequest request){
        Task task = taskMapper.toEntity(request);
        return taskMapper.toDTO(taskRepository.save(task));
    }

    @Override
    public TaskDTO updateTask(Long id, TaskRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task no encontrada"));

        taskMapper.updateEntity(task, request);

        return taskMapper.toDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task no encontrada");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTO> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getTasksByCompleted(Boolean completed) {
        return taskRepository.findByCompleted(completed)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getTasks(Priority priority, Boolean completed) {

        List<Task> tasks;

        if (priority != null && completed != null) {
            tasks = taskRepository.findByPriorityAndCompleted(priority, completed);
        } else if (priority != null) {
            tasks = taskRepository.findByPriority(priority);
        } else if (completed != null) {
            tasks = taskRepository.findByCompleted(completed);
        } else {
            tasks = taskRepository.findAll();
        }

        return tasks.stream()
                .map(taskMapper::toDTO)
                .toList();
    }

}
