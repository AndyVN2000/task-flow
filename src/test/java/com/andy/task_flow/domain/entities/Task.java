package com.andy.task_flow.domain.entities;

import com.andy.task_flow.domain.enums.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import java.util.UUID;

@Entity
public class Task {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private TaskPriority priority;

    // TODO: Perhaps String is not a fitting datatype for dates.
    @Column
    private String dueDate;

    // TODO: It is not so simple as to say that labels are Strings.
    @Column
    private String label;
    
}
