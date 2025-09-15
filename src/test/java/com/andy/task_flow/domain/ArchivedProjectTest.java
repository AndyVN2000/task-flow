package com.andy.task_flow.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.ArchivedProject;
import com.andy.task_flow.domain.entities.TaskImpl;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.ProjectBuilder;

public class ArchivedProjectTest extends ProjectContractTest {

    @Override
    protected Project createProject() {
        return ArchivedProject.of("Foo", "Bar", Instant.MIN, "John Doe", UUID.randomUUID());
    }

    @Override
    protected ProjectBuilder createProjectBuilder() {
        return new ArchivedProject.ArchivedProjectBuilder();
    }

    // @Test
    // public void archivedProjectStillReportsOverdueTasks() {
    //     // Instantiate project with an overdue task
    //     ArchivedProject project;

    //     Task task = project.getTasks().get(0);
    //     Instant dueDate = task.getDueDate().get();
    //     Instant currentDate = Instant.EPOCH;

    //     assertTrue(dueDate.isBefore(currentDate));

    //     assertTrue(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    // }
    
}
