package com.andy.task_flow.domain.entities;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.interfaces.Project;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class ArchivedProject implements Project {

    @Id
    private final UUID id = UUID.randomUUID();

    @OneToMany(mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Instant createdAt;

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

    public List<Task> getTasks() {
        return tasks;
    }

    public boolean isArchived() {
        return true;
    }

    public int getCompletedTaskCount() {
        return 0;
    }

    public boolean hasOverdueTasks(Clock clock) {
        return false;
    }
    
}
