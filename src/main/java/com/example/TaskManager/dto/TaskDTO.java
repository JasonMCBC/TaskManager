package com.example.TaskManager.dto;

//import lombok.Value; con este import vuelve la clase inmutable con getter y constructor sin ecribir

public class TaskDTO {
    private final Long id;
    private final String title;
    private final String description;
    private final String priority;
    private final boolean completed;

    public TaskDTO(Long id, String title, String description, String priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
    }
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getPriority() {
        return priority;
    }
    public boolean isCompleted() {
        return completed;
    }
}
