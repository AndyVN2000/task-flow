package com.andy.task_flow.domain.entities;

import com.andy.task_flow.domain.enums.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import java.util.UUID;
import java.util.Set;
import java.time.LocalDate;

@Entity
public class Task {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private TaskPriority priority;

    @Column
    private LocalDate dueDate;

    // TODO: Study @ManyToMany and @JoinTable on how they work.
    // This tutorial shows how to use the annotations: https://www.baeldung.com/jpa-many-to-many
    @ManyToMany
    @JoinTable(
        name = "task_label",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels;
    

    @ManyToOne
    private Project project;

}
