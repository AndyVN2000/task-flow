package com.andy.task_flow.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.ArchivedProject;
import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.ProjectBuilder;
import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.fixtures.builders.TaskBuilder;

public class ArchivedProjectTest extends ProjectContractTest {
    private Instant currentDate;

    @Override
    protected AbstractProject createProject() {
        return ArchivedProject.of("Foo", "Bar", Instant.MIN, "John Doe", UUID.randomUUID());
    }

    @Override
    protected ProjectBuilder createProjectBuilder() {
        return new ArchivedProject.ArchivedProjectBuilder();
    }

    @BeforeEach
    private void setUp() {
        currentDate = Instant.EPOCH;
    }

    @Test
    public void archivedProjectStillReportsOverdueTasks() {
        // Instantiate project with an overdue task
        TaskBuilder taskBuilder = createTaskBuilder();
        AbstractTask overdueTask = taskBuilder.setDueDate(Optional.of(currentDate.minus(1, ChronoUnit.DAYS)))
            .build();
        ProjectBuilder projectBuilder = createProjectBuilder();
        ArchivedProject project = (ArchivedProject) projectBuilder.addTask(overdueTask).build();

        AbstractTask task = project.getTasks().get(0);
        Instant dueDate = task.getDueDate().get();

        assertTrue(dueDate.isBefore(currentDate));

        assertTrue(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    }

    
    
}
