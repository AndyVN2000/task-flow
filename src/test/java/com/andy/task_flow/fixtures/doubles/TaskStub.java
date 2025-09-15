package com.andy.task_flow.fixtures.doubles;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.enums.TaskStatus;

public class TaskStub implements Task {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private Optional<Instant> dueDate;
    private Instant createdAt;
    private Optional<Instant> completedAt;
    private Project project;

    public TaskStub(UUID id, String title, String description, TaskStatus status, Optional<Instant> dueDate, 
                    Instant createdAt, Optional<Instant> completedAt, Project project) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.project = project;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public Optional<Instant> getDueDate() {
        return dueDate;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public Optional<Instant> getCompletedAt() {
        return completedAt;
    }

    @Override
    public Project getProject() {
        return project;
    }
    
}
