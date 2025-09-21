package com.andy.task_flow.domain.entities.base;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.andy.task_flow.domain.entities.Label;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.enums.TaskStatus;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class AbstractTask implements Task {
    @Id
    @GeneratedValue
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Instant createdAt;

    private Optional<Instant> completedAt;

    private TaskStatus status;

    private Optional<Instant> dueDate;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // TODO: Study @ManyToMany and @JoinTable on how they work.
    // This tutorial shows how to use the annotations: https://www.baeldung.com/jpa-many-to-many
    @ManyToMany
    @JoinTable(
        name = "task_label",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels = new HashSet<>();

    protected AbstractTask(String title, String description, Project project, Optional<Instant> dueDate) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.dueDate = dueDate;
        this.status = TaskStatus.CREATED;
        this.createdAt = Instant.now();
    }

    // getters
    public UUID getId() { 
        return id; 
    }

    public String getTitle() { 
        return title; 
    }

    public String getDescription() {
        return description; 
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public Optional<Instant> getDueDate() {
        return dueDate;
    }

    public Instant getCreatedAt() { 
        return createdAt; 
    }

    public Optional<Instant> getCompletedAt() { 
        return completedAt; 
    }

    @Override
    public Project getProject() {
        return project;
    }
}
