package com.andy.task_flow.domain.entities;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.domain.entities.interfaces.MutableProject;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.domain.exceptions.DuplicateTaskException;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;
import java.time.Clock;
import java.time.Instant;

@Entity
@DiscriminatorValue("FALSE")
public class ProjectImpl extends AbstractProject implements MutableProject {

    @Id
    private final UUID id;

    public static AbstractProject of(String name, String description) {
        return new ProjectImpl(name, description);
    }

    private ProjectImpl(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.createdAt = Instant.now();
    }

    // Project methods

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Optional<Instant> getArchivedAt() {
        return Optional.empty();
    }

    public Optional<String> getArchivedBy() {
        return Optional.empty();
    }

    public boolean isArchived() {
        return false;
    }

    public int getCompletedTaskCount() {
        return 0;
    }

    // MutableProject methods
    public void rename(String newName) {
        this.name = newName;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    public void addTask(AbstractTask task) {
        UUID taskId = task.getId();
        if (tasks.containsKey(taskId)) {
            throw new DuplicateTaskException(
                "Task with id " + task.getId() + " is already in project with id " + this.getId()
            );
        }
        this.tasks.put(task.getId(), task);
    }

    public boolean removeTask(UUID taskId) {
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        
        this.tasks.remove(taskId);
        return true;
    }

    public ArchivedProject archive(String archivedBy, Clock clock) {
        return (ArchivedProject) new ArchivedProject.ArchivedProjectBuilder().
            fromProject(this).
            archivedAt(clock.instant()).
            build();
    }

    // Constructor for ProjectImplBuilder.java
    public ProjectImpl(UUID id, List<AbstractTask> tasks, String name, String description, Instant createdAt) {
        this.id = id;
        this.tasks = tasks.stream().collect(Collectors.toMap(Task::getId, task -> task));
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

}
