package com.andy.task_flow.domain.entities.interfaces;

import java.util.List;
import java.util.UUID;

public interface ProjectBuilder {

    ProjectBuilder setId(UUID id);

    ProjectBuilder setTasks(List<Task> tasks);

    ProjectBuilder addTask(Task task);

    ProjectBuilder setName(String name);

    ProjectBuilder setDescription(String description);

    Project build();

}
