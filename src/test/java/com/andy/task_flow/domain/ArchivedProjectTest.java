package com.andy.task_flow.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.ArchivedProject;
import com.andy.task_flow.domain.entities.Task;
import com.andy.task_flow.domain.entities.interfaces.Project;

public class ArchivedProjectTest extends ProjectContractTest {

    @Override
    protected Project createProject() {
        return ArchivedProject.of("Foo", "Bar", Instant.MIN, "John Doe");
    }

    @Test
    public void archivedProjectStillReportsOverdueTasks() {
        // Instantiate project with an overdue task
        ArchivedProject project;

        Task task = project.getTasks().get(0);
        Instant dueDate = task.getDueDate().get();
        Instant currentDate = Instant.EPOCH;

        assertTrue(dueDate.isBefore(currentDate));

        assertTrue(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    }
    
}
