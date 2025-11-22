package com.andy.task_flow.fixtures.builders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.ProjectBuilder;
import com.andy.task_flow.domain.entities.base.AbstractTask;

public class ProjectImplBuilder implements ProjectBuilder {
    private UUID id;
    private List<AbstractTask> tasks = new ArrayList<>();
    private String name;
    private String description;
    private Instant createdAt;

    @Override
    public ProjectBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public ProjectBuilder setTasks(List<AbstractTask> tasks) {
        this.tasks = tasks;
        return this;
    }

    @Override
    public ProjectBuilder addTask(AbstractTask task) {
        this.tasks.add(task);
        return this;
    }

    @Override
    public ProjectBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ProjectBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public AbstractProject build() {
        return new ProjectImpl(id, tasks, name, description, createdAt);
    }
    
}
