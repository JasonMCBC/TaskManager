package com.example.TaskManager.dto;

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String importance;
    private boolean completed;

    public TaskDTO(Long id, String title, String description, String importance, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.importance = importance;
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
    public String getImportance() {
        return importance;
    }
    public boolean isCompleted() {
        return completed;
    }
}
