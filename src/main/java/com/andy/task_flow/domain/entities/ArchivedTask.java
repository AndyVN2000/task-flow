package com.andy.task_flow.domain.entities;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.enums.TaskStatus;

import jakarta.persistence.Entity;

@Entity
public class ArchivedTask extends AbstractTask implements Task {

    private ArchivedTask(String title, String description, Project project, Optional<Instant> dueDate) {
        super(title, description, project, dueDate);
    }

    public static ArchivedTask of(String title, String description, Project project, Optional<Instant> dueDate) {
        if (project == null) {
            throw new NullPointerException("Task must belong to a project");
        }
        return new ArchivedTask(title, description, project, dueDate);
    }

}
