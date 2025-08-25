package com.andy.task_flow.domain.entities;

import com.andy.task_flow.domain.entities.interfaces.MutableProject;
import com.andy.task_flow.domain.entities.interfaces.Project;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;

import java.util.UUID;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.time.Clock;
import java.time.Instant;

@Entity
public class ProjectImpl implements MutableProject {

    private boolean isArchived;
    
    @Id
    private final UUID id = UUID.randomUUID();

    /**
     * andistoev/onion‑architecture‑with‑spring‑boot
     * Andistoev uses a LinkedHashSet, instead of HashSet in this kind of situation.
     * So why would I use LinkedHashSet in my specific case?
     * It comes with the advantage of ordering items based on the order they er added
     * to the set. Does it matter for Projects that contains sets of Tasks?
     */
    @OneToMany(mappedBy = "project")
    private Set<Task> tasks = new HashSet<>();

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Instant createdAt;

    public static Project of(String name, String description) {
        return new ProjectImpl(name, description);
    }

    private ProjectImpl(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = Instant.now();
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
        return Optional.empty();
    }

    public Optional<String> getArchivedBy() {
        return Optional.empty();
    }

    public List<Task> getTasks() {
        return null;
    }

    public boolean isArchived() {
        return false;
    }

    public int getCompletedTaskCount() {
        return 0;
    }

    public boolean hasOverdueTasks(Clock clock) {
        return false;
    }

}
