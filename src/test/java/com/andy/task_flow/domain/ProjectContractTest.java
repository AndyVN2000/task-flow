package com.andy.task_flow.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.interfaces.ProjectBuilder;
import com.andy.task_flow.domain.enums.TaskStatus;
import com.andy.task_flow.fixtures.builders.TaskBuilder;
import com.andy.task_flow.fixtures.builders.TaskStubBuilder;

/**
 * This testing pattern is based on: https://www.baeldung.com/java-junit-verify-interface-contract
 * The advantage of this pattern is that it avoids duplication of test cases on methods
 * that are common for both ProjectImpl and ArchivedProject.
 * Example: both classes have a getName() method. It has the same behavior in both classes.
 *          If we simply wrote separate test classes for each implementing class,
 *          then we would end up duplicating the test case that getName() should return a string.
 */

public abstract class ProjectContractTest {
    
    protected abstract AbstractProject createProject();

    protected abstract ProjectBuilder createProjectBuilder();

    protected TaskBuilder createTaskBuilder() {
        return new TaskStubBuilder();
    }

    @Test
    public void nameShouldNotBeNull() {
        AbstractProject project = createProject();
        assertNotNull(project.getName());
    }
    
    @Test
    public void descriptionShouldNotBeNull() {
        AbstractProject project = createProject();
        assertNotNull(project.getDescription());
    }

    @Test
    public void taskListShouldNotBeNull() {
        AbstractProject project = createProject();
        assertNotNull(project.getTasks());
    }

    @Test
    public void createdAtShouldNotBeNull() {
        AbstractProject project = createProject();
        assertNotNull(project.getCreatedAt());
    }

    @Test
    public void archivedAtIsAnOptional() {
        AbstractProject project = createProject();
        assertTrue(project.getArchivedAt() instanceof Optional);
    }

    @Test
    public void archivedByIsAnOptional() {
        AbstractProject project = createProject();
        assertTrue(project.getArchivedBy() instanceof Optional);
    }

    @Test
    public void isArchivedShouldNotBeNull() {
        AbstractProject project = createProject();
        assertNotNull(project.isArchived());
    }

    @Test
    public void getCompletedTaskCountShouldNotBeNull() {
        AbstractProject project = createProject();
        assertNotNull(project.getCompletedTaskCount());
    }

    @Test
    public void hasOverdueTasksShouldNotBeNull() {
        AbstractProject project = createProject();
        assertNotNull(project.hasOverdueTasks(Clock.fixed(Instant.MAX, ZoneId.of("Europe/Paris"))));
    }

    @Test
    public void noTasksShouldMeanNoOverdueTasks() {
        // Instantiate project with an empty list of tasks.
        List<AbstractTask> tasks = new ArrayList<>();
        ProjectBuilder builder = createProjectBuilder();
        AbstractProject project = builder.
            setTasks(tasks).
            build();

        assertFalse(project.hasOverdueTasks(Clock.fixed(Instant.MAX, ZoneId.of("Europe/Paris"))));
    }

    /**
     * Note: 
     * I was in a terrible dilemma. I have to set up a test case scenario
     *  where the project has some particular task. This test case and many others
     *  have shared behavior for both `ProjectImpl` and `ArchivedProject` and 
     *  therefore, the generation of a `AbstractProject` object had to be agnostic.
     *  
     * I used a builder design pattern to achieve this agnostic behavior in this
     *  abstract test class. But before I could create my desired project for the
     *  test case using the builder, I had to instantiate a `AbstractTask` object to pass on
     *  to the builder. But to instantiate a `AbstractTask` object, business rules required
     *  me to set a `AbstractProject` object as its field via the factory method of `AbstractTask`.
     *  That is due to the business rule that forces me to make the `project` field
     *  in `AbstractTask` to be `final`.
     * 
     * There was a circular reference that stopped me from setting up my test case.
     *  To circumvent this, I assigned an arbitrary `AbstractProject` object to the `AbstractTask`
     *  object and then added the task to another project.
     *  But I am not sure if this violates my business rules, since this workaround
     *  would only occur in unit tests.
     * 
     * As I write this note on my thoughts, I came to realize; was I supposed to
     *  write and use a test double for `AbstractTask` rather than using a real `AbstractTask` 
     *  object that is used for production code?
     *  Yea, I should most likely write a test stub `TaskStub` and do the 3-1-2 step
     *  on the `AbstractTask.java`.
     */
    @Test
    public void tasksWithoutDueDatesShouldNeverBeOverdue() {
        // Instantiate project with a task that has no due date
        TaskBuilder taskBuilder = createTaskBuilder();
        AbstractTask task = taskBuilder.setDueDate(Optional.empty()).build();
        ProjectBuilder builder = createProjectBuilder();
        AbstractProject project = builder.addTask(task).build();
        assertFalse(project.hasOverdueTasks(Clock.fixed(Instant.MAX, ZoneId.of("Europe/Paris"))));
    }

    @Test
    public void tasksDueInFutureShouldNotBeOverdue() {
        Instant futureDueDate = Instant.EPOCH.plus(1, ChronoUnit.DAYS);
        TaskBuilder taskBuilder = createTaskBuilder();
        AbstractTask taskDueInFuture = taskBuilder.setDueDate(Optional.of(futureDueDate)).build();
        // Instantiate project with a task that is due in Instant.EPOCH plus 1 day.
        ProjectBuilder projectBuilder = createProjectBuilder();
        AbstractProject project = projectBuilder.addTask(taskDueInFuture).build();
        
        // Should be equal to Instant.EPOCH.plus(1, ChronoUnit.DAYS)
        Instant taskDueDate = project.getTasks().get(0).getDueDate().get();
        assertEquals(futureDueDate, taskDueDate);

        // Verify that the current date is before the due date.
        Instant currentDate = Instant.EPOCH;
        assertTrue(currentDate.isBefore(taskDueDate));

        assertFalse(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    }

    @Test
    public void completedTasksShouldNeverBeOverdue() {
        Instant completionDate = Instant.EPOCH;
        Instant dueDate = completionDate.minus(1, ChronoUnit.DAYS);
        assertTrue(completionDate.isAfter(dueDate));
        Instant currentDate = completionDate.plus(1, ChronoUnit.DAYS);
        assertTrue(dueDate.isBefore(currentDate));

        TaskBuilder taskBuilder = createTaskBuilder();
        AbstractTask completedTask = taskBuilder.setStatus(TaskStatus.COMPLETED).
            setCompletedAt(Optional.of(completionDate)).
            setDueDate(Optional.of(dueDate)).
            build();

        // Instantiate project with a task that is due in the past, but its status is completed
        ProjectBuilder projectBuilder = createProjectBuilder();
        AbstractProject project = projectBuilder.addTask(completedTask).build();

        AbstractTask task = project.getTasks().get(0);
        assertTrue(task.getStatus() == TaskStatus.COMPLETED);

        assertFalse(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    }

    @Test
    public void taskDueInPastAndNotCompletedShouldBeOverdue() {
        Instant currentDate = Instant.EPOCH;
        Instant dueDate = currentDate.minus(1, ChronoUnit.DAYS);

        TaskBuilder taskBuilder = createTaskBuilder();
        AbstractTask overdueTask = taskBuilder.setCompletedAt(Optional.empty()).
            setDueDate(Optional.of(dueDate)).
            setStatus(TaskStatus.CREATED).
            build();
        // Instantiate project with a task that is due in the past and is not completed
        ProjectBuilder projectBuilder = createProjectBuilder();
        AbstractProject project = projectBuilder.addTask(overdueTask).build();

        AbstractTask task = project.getTasks().get(0);
        TaskStatus status = task.getStatus();
        assertTrue(status != TaskStatus.COMPLETED);
        Instant taskDueDate = task.getDueDate().get();
        assertTrue(currentDate.isAfter(taskDueDate));

        assertTrue(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    }

    @Test
    public void tasksDueExactlyAtCurrentTimeShouldNotBeOverdue() {
        Instant currentDate = Instant.EPOCH;

        TaskBuilder taskBuilder = createTaskBuilder();
        AbstractTask taskDueToday = taskBuilder.setDueDate(Optional.of(currentDate))
            .build();

        // Instantiate project with task due at Instant.EPOCH
        ProjectBuilder projectBuilder = createProjectBuilder();
        AbstractProject project = projectBuilder.addTask(taskDueToday)
            .build();

        AbstractTask task = project.getTasks().get(0);
        Instant dueDate = task.getDueDate().get();
        assertEquals(dueDate, currentDate);
        
        assertFalse(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    }

    /**
     * If there are several tasks that are not overdue but a single is overdue, then
     * project.hasOverdueTasks() returns true.
     */
    @Test
    public void oneTaskBeingOverdueAmongManyTasksReturnsTrue() {
        Instant currentDate = Instant.EPOCH;
        // Instantiate a project with two tasks. One is not overdue and the other is.
        TaskBuilder overdueTaskBuilder = createTaskBuilder();
        AbstractTask overdueTask = overdueTaskBuilder.setDueDate(Optional.of(currentDate.minus(1, ChronoUnit.DAYS)))
            .setId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
            .build();

        assertTrue(overdueTask.getDueDate().get().isBefore(currentDate));
        
        TaskBuilder notOverdueTaskBuilder = createTaskBuilder();
        AbstractTask notOverdueTask = notOverdueTaskBuilder.setDueDate(Optional.of(currentDate.plus(1, ChronoUnit.DAYS)))
            .setId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
            .build();

        assertTrue(notOverdueTask.getDueDate().get().isAfter(currentDate));

        ProjectBuilder projectBuilder = createProjectBuilder();
        AbstractProject project = projectBuilder.addTask(overdueTask)
            .addTask(notOverdueTask)
            .build();

        AbstractTask task0 = project.getTasks().get(0);
        AbstractTask task1 = project.getTasks().get(1);

        // Due in Instant.EPOCH.minus(1, ChronoUnit.DAYS)
        Instant dueDate0 = task0.getDueDate().get();
        // Due in Instant.EPOCH.plus(1, ChronoUnit.DAYS)
        Instant dueDate1 = task1.getDueDate().get();

        assertTrue(dueDate0.isBefore(currentDate));
        assertTrue(dueDate1.isAfter(currentDate));

        assertTrue(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    }

}
