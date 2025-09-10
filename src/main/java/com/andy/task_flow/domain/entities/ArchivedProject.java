package com.andy.task_flow.domain.entities;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.ProjectBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class ArchivedProject extends AbstractProject implements Project {

    @Id
    protected final UUID id;

    @Column
    private Instant archivedAt;

    @Column
    private String archivedBy;

    public static Project of(String name, String description, Instant createdAt, String archivedBy, UUID id) {
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
        private List<Task> tasks;
        private String name;
        private String description;
        private Instant createdAt;
        private Instant archivedAt;

        public ArchivedProjectBuilder fromProject(Project project) {
            this.id = project.getId();
            this.tasks = project.getTasks();
            this.name = project.getName();
            this.description = project.getDescription();
            this.createdAt = project.getCreatedAt();
            return this;
        }

        public ProjectBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public ProjectBuilder setTasks(List<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public ProjectBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProjectBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ArchivedProjectBuilder archivedAt(Instant archivedAt) {
            this.archivedAt = archivedAt;
            return this;
        }

        public Project build() {
            Objects.requireNonNull(id, "id must not be null");
            Objects.requireNonNull(name, "name must not be null");
            Objects.requireNonNull(createdAt, "createdAt must not be null");
            Objects.requireNonNull(archivedAt, "archivedAt must not be null");
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
    
}
