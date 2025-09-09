package com.andy.task_flow.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.interfaces.Project;

/**
 * This testing pattern is based on: https://www.baeldung.com/java-junit-verify-interface-contract
 * The advantage of this pattern is that it avoids duplication of test cases on methods
 * that are common for both ProjectImpl and ArchivedProject.
 * Example: both classes have a getName() method. It has the same behavior in both classes.
 *          If we simply wrote separate test classes for each implementing class,
 *          then we would end up duplicating the test case that getName() should return a string.
 */

public abstract class ProjectContractTest {
    
    protected abstract Project createProject();

    @Test
    public void projectIdShouldNotBeNull() {
        Project project = createProject();
        assertNotNull(project.getId());
    }

    @Test
    public void nameShouldNotBeNull() {
        Project project = createProject();
        assertNotNull(project.getName());
    }
    
    @Test
    public void descriptionShouldNotBeNull() {
        Project project = createProject();
        assertNotNull(project.getDescription());
    }

    @Test
    public void taskListShouldNotBeNull() {
        Project project = createProject();
        assertNotNull(project.getTasks());
    }

    @Test
    public void createdAtShouldNotBeNull() {
        Project project = createProject();
        assertNotNull(project.getCreatedAt());
    }

    @Test
    public void archivedAtIsAnOptional() {
        Project project = createProject();
        assertTrue(project.getArchivedAt() instanceof Optional);
    }

    @Test
    public void archivedByIsAnOptional() {
        Project project = createProject();
        assertTrue(project.getArchivedBy() instanceof Optional);
    }

    @Test
    public void isArchivedShouldNotBeNull() {
        Project project = createProject();
        assertNotNull(project.isArchived());
    }

    @Test
    public void getCompletedTaskCountShouldNotBeNull() {
        Project project = createProject();
        assertNotNull(project.getCompletedTaskCount());
    }

    @Test
    public void hasOverdueTasksShouldNotBeNull() {
        Project project = createProject();
        assertNotNull(project.hasOverdueTasks(Clock.fixed(Instant.MAX, ZoneId.of("Europe/Paris"))));
    }

}
