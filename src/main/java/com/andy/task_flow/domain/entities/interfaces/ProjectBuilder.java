package com.andy.task_flow.domain.entities.interfaces;

import java.util.List;
import java.util.UUID;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.base.AbstractTask;

public interface ProjectBuilder {

    ProjectBuilder setId(UUID id);

    ProjectBuilder setTasks(List<AbstractTask> tasks);

    ProjectBuilder addTask(AbstractTask task);

    ProjectBuilder setName(String name);

    ProjectBuilder setDescription(String description);

    AbstractProject build();

}
