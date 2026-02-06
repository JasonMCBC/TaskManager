package com.example.taskmanager.dto;

import com.example.taskmanager.model.Priority;

public class TaskDTO {
    private final Long id;
    private final String title;
    private final String description;
    private final Priority priority;
    private final boolean completed;

    public TaskDTO(Long id, String title, String description, Priority priority, boolean completed) {
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
    public Priority getPriority() {
        return priority;
    }
    public boolean isCompleted() {
        return completed;
    }
}
