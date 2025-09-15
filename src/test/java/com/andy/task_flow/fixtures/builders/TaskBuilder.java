package com.andy.task_flow.fixtures.builders;

import java.util.UUID;

import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.enums.TaskStatus;

public interface TaskBuilder {

    public TaskBuilder setId(UUID id);

    public TaskBuilder setStatus(TaskStatus status);

    public Task build();

}
