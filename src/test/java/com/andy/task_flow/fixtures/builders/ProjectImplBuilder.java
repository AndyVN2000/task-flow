package com.andy.task_flow.fixtures.builders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.ProjectBuilder;
import com.andy.task_flow.domain.entities.interfaces.Task;

public class ProjectImplBuilder implements ProjectBuilder {
    private UUID id;
    private List<Task> tasks = new ArrayList<>();
    private String name;
    private String description;
    private Instant createdAt;

    @Override
    public ProjectBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public ProjectBuilder setTasks(List<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    @Override
    public ProjectBuilder addTask(Task task) {
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
    public Project build() {
        return new ProjectImpl(id, tasks, name, description, createdAt);
    }
    
}
