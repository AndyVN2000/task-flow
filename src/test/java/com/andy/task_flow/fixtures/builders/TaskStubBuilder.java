package com.andy.task_flow.fixtures.builders;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.enums.TaskStatus;
import com.andy.task_flow.fixtures.doubles.TaskStub;

public class TaskStubBuilder implements TaskBuilder {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private Optional<Instant> dueDate;
    private Instant createdAt;
    private Optional<Instant> completedAt;
    private Project project;

    @Override
    public TaskBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public TaskBuilder setStatus(TaskStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public TaskBuilder setDueDate(Optional<Instant> dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    @Override
    public TaskBuilder setCompletedAt(Optional<Instant> completedAt) {
        this.completedAt = completedAt;
        return this;
    }

    @Override
    public Task build() {
        return new TaskStub(id, title, description, status, dueDate, createdAt, completedAt, project);
    }

}
