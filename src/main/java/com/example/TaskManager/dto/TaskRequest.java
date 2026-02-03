package com.example.TaskManager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.example.TaskManager.model.Priority;

public class TaskRequest {
    @NotBlank(message="El título no puede estar en blanco")
    @Size(min=3, max=100,message="Debe contener entre 3 y 100 caracteres")
    private String title;

    @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
    private String description;

    @NotBlank(message="La prioridad no puede estar vacía")
    @Size(min=3, max=100,message="Debe contener entre 3 y 100 caracteres")
    private Priority priority;

    @NotNull(message="Se debe indicar si esta completa o no la tarea")
    private boolean completed;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
