package com.andy.task_flow.domain.entities;

import com.andy.task_flow.domain.entities.interfaces.MutableProject;
import com.andy.task_flow.domain.entities.interfaces.Project;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.time.Clock;
import java.time.Instant;

@Entity
public class ProjectImpl implements MutableProject {
    
    @Id
    private final UUID id = UUID.randomUUID();

    /**
     * andistoev/onion‑architecture‑with‑spring‑boot
     * Andistoev uses a LinkedHashSet, instead of HashSet in this kind of situation.
     * So why would I use LinkedHashSet in my specific case?
     * It comes with the advantage of ordering items based on the order they er added
     * to the set. Does it matter for Projects that contains sets of Tasks?
     * For now, we define tasks as a List, since this is what ChatGPT (our fake customer)
     * specifies.
     * TODO: Ask our 'customer' for user stories on what end user can do with a collection
     *       of tasks in a project.
     */
    @OneToMany(mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();

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

    public List<Task> getTasks() {
        return tasks;
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
