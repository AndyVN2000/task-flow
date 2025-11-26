package com.andy.task_flow.domain.entities;

import java.time.Clock;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.ProjectBuilder;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.domain.exceptions.ChangeNotAllowedException;
import com.andy.task_flow.domain.exceptions.ProjectAlreadyArchivedException;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@DiscriminatorValue("TRUE")
public class ArchivedProject extends AbstractProject {

    @Column
    private Instant archivedAt;

    @Column
    private String archivedBy;

    public static AbstractProject of(String name, String description, Instant createdAt, String archivedBy, UUID id) {
        return new ArchivedProject(name, description, createdAt, archivedBy, id);
    }

    private ArchivedProject(String name, String description, Instant createdAt, String archivedBy, UUID id) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.archivedAt = Instant.now();
        this.archivedBy = archivedBy;
        this.id = id;

    }

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
        return Optional.of(archivedAt);
    }

    public Optional<String> getArchivedBy() {
        return Optional.of(archivedBy);
    }

    public boolean isArchived() {
        return true;
    }

    public int getCompletedTaskCount() {
        return 0;
    }

    // Builder
    public static class ArchivedProjectBuilder implements ProjectBuilder {
        private UUID id;
        private Map<UUID, AbstractTask> tasks = new LinkedHashMap<>();
        private String name;
        private String description;
        private Instant createdAt;
        private Instant archivedAt;

        public ArchivedProjectBuilder fromProject(AbstractProject project) {
            this.id = project.getId();
            this.tasks = project.getTasks().stream().collect(Collectors.toMap(Task::getId, task -> task));
            this.name = project.getName();
            this.description = project.getDescription();
            this.createdAt = project.getCreatedAt();
            return this;
        }

        @Override
        public ProjectBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        @Override
        public ProjectBuilder setTasks(List<AbstractTask> tasks) {
            this.tasks = tasks.stream().collect(Collectors.toMap(Task::getId, task -> task));
            return this;
        }

        @Override
        public ProjectBuilder addTask(AbstractTask task) {
            this.tasks.put(task.getId(), task);
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

        public ArchivedProjectBuilder archivedAt(Instant archivedAt) {
            this.archivedAt = archivedAt;
            return this;
        }

        public AbstractProject build() {
            return new ArchivedProject(this);
        }

    }

    private ArchivedProject(ArchivedProjectBuilder builder) {
        this.id = builder.id;
        this.tasks = builder.tasks;
        this.name = builder.name;
        this.description = builder.description;
        this.createdAt = builder.createdAt;
        this.archivedAt = builder.archivedAt;
    }

    @Override
    public void rename(String newName) {
        throw new UnsupportedOperationException("Archived project can not be renamed");
    }

    @Override
    public void changeDescription(String newDescription) {
        throw new UnsupportedOperationException("Archived project can not get description changed");
    }

    @Override
    public void addTask(AbstractTask task) {
        throw new UnsupportedOperationException("Can not add new tasks to archived project");
    }

    @Override
    public boolean removeTask(UUID taskId) {
        throw new ChangeNotAllowedException("Can not remove task from archived project");
    }

    @Override
    public ArchivedProject archive(String archivedBy, Clock clock) {
        throw new ProjectAlreadyArchivedException("Already archived");
    }
    
}
