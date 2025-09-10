package com.andy.task_flow.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.Task;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.enums.TaskStatus;

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

    // @Test
    // public void noTasksShouldMeanNoOverdueTasks() {
    //     // Instantiate project with an empty list of tasks.
    //     Project project;
    //     assertFalse(project.hasOverdueTasks(Clock.fixed(Instant.MAX, ZoneId.of("Europe/Paris"))));
    // }

    // @Test
    // public void tasksWithoutDueDatesShouldNeverBeOverdue() {
    //     // Instantiate project with a task that has no due date
    //     Project project;
    //     assertFalse(project.hasOverdueTasks(Clock.fixed(Instant.MAX, ZoneId.of("Europe/Paris"))));
    // }

    // @Test
    // public void tasksDueInFutureShouldNotBeOverdue() {
    //     Instant futureDueDate = Instant.EPOCH.plus(1, ChronoUnit.DAYS);
    //     // Instantiate project with a task that is due in Instant.EPOCH plus 1 day.
    //     Project project;
        
    //     // Should be equal to Instant.EPOCH.plus(1, ChronoUnit.DAYS)
    //     Instant taskDueDate = project.getTasks().get(0).getDueDate().get();

    //     Instant currentDate = Instant.EPOCH;
    //     assertTrue(currentDate.isBefore(taskDueDate));

    //     assertFalse(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    // }

    // @Test
    // public void completedTasksShouldNeverBeOverdue() {
    //     // Instantiate project with a task that is due in the past, but its status is completed
    //     Project project;

    //     Task task = project.getTasks().get(0);
    //     assertTrue(task.getStatus() == TaskStatus.DONE);

    //     Instant currentDate = Instant.EPOCH.plus(1, ChronoUnit.DAYS);
    //     // dueDate should be Instant.EPOCH
    //     Instant dueDate = task.getDueDate().get();
    //     assertTrue(dueDate.isBefore(currentDate));

    //     assertFalse(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    // }

    // @Test
    // public void taskDueInPastAndNotCompletedShouldBeOverdue() {
    //     // Instantiate project with a task that is due in the past and is not completed
    //     Project project;

    //     Task task = project.getTasks().get(0);
    //     TaskStatus status = task.getStatus();
    //     assertTrue(status == TaskStatus.TODO || status == TaskStatus.IN_PROGRESS || status == TaskStatus.BLOCKED);

    //     Instant currentDate = Instant.EPOCH.plus(1, ChronoUnit.DAYS);
    //     // dueDate should be Instant.EPOCH
    //     Instant dueDate = task.getDueDate().get();
    //     assertTrue(dueDate.isBefore(currentDate));

    //     assertTrue(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    // }

    // @Test
    // public void tasksDueExactlyAtCurrentTimeShouldNotBeOverdue() {
    //     // Instantiate project with task due at Instant.EPOCH
    //     Project project;

    //     Task task = project.getTasks().get(0);
    //     Instant dueDate = task.getDueDate().get();
    //     Instant currentDate = Instant.EPOCH;
    //     assertEquals(dueDate, currentDate);
        
    //     assertFalse(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    // }

    // /**
    //  * If there are several tasks that are not overdue but a single is overdue, then
    //  * project.hasOverdueTasks() returns true.
    //  */
    // @Test
    // public void oneTaskBeingOverdueAmongManyTasksReturnsTrue() {
    //     // Instantiate a project with two tasks. One is not overdue and the other is.
    //     Project project;

    //     Task task0 = project.getTasks().get(0);
    //     Task task1 = project.getTasks().get(1);

    //     // Due in Instant.EPOCH.minus(1, ChronoUnit.DAYS)
    //     Instant dueDate0 = task0.getDueDate().get();
    //     // Due in Instant.EPOCH.plus(1, ChronoUnit.DAYS)
    //     Instant dueDate1 = task1.getDueDate().get();

    //     Instant currentDate = Instant.EPOCH;

    //     assertTrue(dueDate0.isBefore(currentDate));
    //     assertTrue(dueDate1.isAfter(currentDate));

    //     assertTrue(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    // }

}
