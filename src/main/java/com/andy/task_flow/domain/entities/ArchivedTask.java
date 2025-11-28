package com.andy.task_flow.domain.entities;

import java.time.Instant;
import java.util.Optional;

import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.base.AbstractTask;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TRUE")
public class ArchivedTask extends AbstractTask {

    // Default no-argument constructor
    public ArchivedTask(){}

    private ArchivedTask(String title, String description, AbstractProject project, Optional<Instant> dueDate) {
        super(title, description, project, dueDate);
    }

    public static ArchivedTask of(String title, String description, AbstractProject project, Optional<Instant> dueDate) {
        if (project == null) {
            throw new NullPointerException("Task must belong to a project");
        }
        return new ArchivedTask(title, description, project, dueDate);
    }

}
