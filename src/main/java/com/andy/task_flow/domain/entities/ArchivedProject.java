package com.andy.task_flow.domain.entities;

import java.time.Instant;
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
    
}
