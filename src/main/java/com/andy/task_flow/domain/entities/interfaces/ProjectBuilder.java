package com.andy.task_flow.domain.entities.interfaces;

import java.util.List;
import java.util.UUID;

import com.andy.task_flow.domain.entities.Task;

public interface ProjectBuilder {

    ProjectBuilder setId(UUID id);

    ProjectBuilder setTasks(List<Task> tasks);

    ProjectBuilder setName(String name);

    ProjectBuilder setDescription(String description);

    Project build();

}
