package com.andy.task_flow.domain.entities;

import com.andy.task_flow.domain.enums.*;
import com.andy.task_flow.domain.entities.interfaces.Project;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import java.util.UUID;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;
import java.time.Instant;

@Entity
public class Task {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private TaskStatus status = TaskStatus.TODO;

    @Column(nullable = false)
    private TaskPriority priority;

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Optional<Instant> dueDate;

    @Column
    private Optional<Instant> completedAt;

    // TODO: Study @ManyToMany and @JoinTable on how they work.
    // This tutorial shows how to use the annotations: https://www.baeldung.com/jpa-many-to-many
    @ManyToMany
    @JoinTable(
        name = "task_label",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels = new HashSet<>();
    

    private Task(String title, Project project, TaskPriority priority, Optional<Instant> dueDate) {
        this.title = title;
        this.project = project;
        this.priority = priority;
        this.dueDate = dueDate;
    }
    
    public static Task of(String title, Project project, Optional<TaskPriority> priority, Optional<Instant> dueDate) {
        if (project == null) {
            throw new NullPointerException("Task must belong to a project");
        }
        TaskPriority a = priority.isPresent() ? priority.get() : TaskPriority.MEDIUM;
        return new Task(title, project, a, dueDate);
    }

    public Optional<Instant> getDueDate() {
        return dueDate;
    }

    /**
     * It is inappropriate to write a method only for the sake of testing.
     * If having this getter is meaningful to the domain or other parts of the system might use this,
     * then it is acceptable to have this method.
     * But I only wrote it for the sake of testing, so this is deprecated for now and to be considered
     * non-existent.
     * @return
     */
    @Deprecated
    public Project getProject() {
        return project;
    }

}
