package com.andy.task_flow.domain.entities;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.interfaces.Project;

import jakarta.persistence.Column;

public class ArchivedProject extends AbstractProject implements Project {

    @Column
    private Instant archivedAt;

    @Column
    private String archivedBy;

    public static Project of(String name, String description, Instant createdAt, String archivedBy) {
        return new ArchivedProject(name, description, createdAt, archivedBy);
    }

    private ArchivedProject(String name, String description, Instant createdAt, String archivedBy) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.archivedAt = Instant.now();
        this.archivedBy = archivedBy;

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
    public static class ArchivedProjectBuilder {
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

        public ArchivedProjectBuilder archivedAt(Instant archivedAt) {
            this.archivedAt = archivedAt;
            return this;
        }

        public ArchivedProject build() {
            return new ArchivedProject(this);
        }

    }

    private ArchivedProject(ArchivedProjectBuilder builder) {
        this.name = builder.name;
    }
    
}
