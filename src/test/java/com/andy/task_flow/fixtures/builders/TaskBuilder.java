package com.andy.task_flow.fixtures.builders;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.enums.TaskStatus;

public interface TaskBuilder {

    public TaskBuilder setId(UUID id);

    public TaskBuilder setStatus(TaskStatus status);

    public TaskBuilder setDueDate(Optional<Instant> dueDate);

    public TaskBuilder setCompletedAt(Optional<Instant> completedAt);

    public TaskBuilder setProject(AbstractProject project);

    public Task build();

}
