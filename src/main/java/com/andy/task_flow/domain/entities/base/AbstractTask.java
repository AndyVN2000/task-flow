package com.andy.task_flow.domain.entities.base;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.interfaces.Task;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class AbstractTask implements Task {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Instant createdAt;

    private Optional<Instant> completedAt;

    // getters
    public UUID getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public Instant getCreatedAt() { return createdAt; }

    public Optional<Instant> getCompletedAt() { return completedAt; }
}
