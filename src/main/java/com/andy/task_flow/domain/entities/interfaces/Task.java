package com.andy.task_flow.domain.entities.interfaces;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.enums.TaskStatus;

public interface Task {

    public UUID getId();

    public String getTitle();

    public String getDescription();
    
    public TaskStatus getStatus();

    public Optional<Instant> getDueDate();

    public Instant getCreatedAt();
    
    public Optional<Instant> getCompletedAt();

    public AbstractProject getProject();

}
