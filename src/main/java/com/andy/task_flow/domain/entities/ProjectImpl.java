package com.andy.task_flow.domain.entities;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.interfaces.MutableProject;
import com.andy.task_flow.domain.entities.interfaces.Project;

import jakarta.persistence.Entity;

import java.util.UUID;
import java.util.Optional;
import java.time.Clock;
import java.time.Instant;

@Entity
public class ProjectImpl extends AbstractProject implements MutableProject {

    public static Project of(String name, String description) {
        return new ProjectImpl(name, description);
    }

    private ProjectImpl(String name, String description) {
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

    }

    public void changeDescription(String newDescription) {

    }

    public void addTask(Task task) {

    }

    public void removeTask(UUID taskId) {

    }

    public ArchivedProject archive(String archivedBy, Clock clock) {
        return null;
    }

}
