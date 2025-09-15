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

import com.andy.task_flow.domain.entities.TaskImpl;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.ProjectBuilder;
import com.andy.task_flow.domain.enums.TaskPriority;
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
    
    protected abstract Project createProject();

    protected abstract ProjectBuilder createProjectBuilder();

    protected TaskBuilder createTaskBuilder() {
        return new TaskStubBuilder();
    }

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

    @Test
    public void noTasksShouldMeanNoOverdueTasks() {
        // Instantiate project with an empty list of tasks.
        List<Task> tasks = new ArrayList<>();
        ProjectBuilder builder = createProjectBuilder();
        Project project = builder.
            setTasks(tasks).
            build();

        assertFalse(project.hasOverdueTasks(Clock.fixed(Instant.MAX, ZoneId.of("Europe/Paris"))));
    }

    /**
     * Note: 
     * I was in a terrible dilemma. I have to set up a test case scenario
     *  where the project has some particular task. This test case and many others
     *  have shared behavior for both `ProjectImpl` and `ArchivedProject` and 
     *  therefore, the generation of a `Project` object had to be agnostic.
     *  
     * I used a builder design pattern to achieve this agnostic behavior in this
     *  abstract test class. But before I could create my desired project for the
     *  test case using the builder, I had to instantiate a `Task` object to pass on
     *  to the builder. But to instantiate a `Task` object, business rules required
     *  me to set a `Project` object as its field via the factory method of `Task`.
     *  That is due to the business rule that forces me to make the `project` field
     *  in `Task` to be `final`.
     * 
     * There was a circular reference that stopped me from setting up my test case.
     *  To circumvent this, I assigned an arbitrary `Project` object to the `Task`
     *  object and then added the task to another project.
     *  But I am not sure if this violates my business rules, since this workaround
     *  would only occur in unit tests.
     * 
     * As I write this note on my thoughts, I came to realize; was I supposed to
     *  write and use a test double for `Task` rather than using a real `Task` 
     *  object that is used for production code?
     *  Yea, I should most likely write a test stub `TaskStub` and do the 3-1-2 step
     *  on the `Task.java`.
     */
    @Test
    public void tasksWithoutDueDatesShouldNeverBeOverdue() {
        // Instantiate project with a task that has no due date
        TaskBuilder taskBuilder = createTaskBuilder();
        Task task = taskBuilder.setDueDate(Optional.empty()).build();
        ProjectBuilder builder = createProjectBuilder();
        Project project = builder.addTask(task).build();
        assertFalse(project.hasOverdueTasks(Clock.fixed(Instant.MAX, ZoneId.of("Europe/Paris"))));
    }

    @Test
    public void tasksDueInFutureShouldNotBeOverdue() {
        Instant futureDueDate = Instant.EPOCH.plus(1, ChronoUnit.DAYS);
        TaskBuilder taskBuilder = createTaskBuilder();
        Task taskDueInFuture = taskBuilder.setDueDate(Optional.of(futureDueDate)).build();
        // Instantiate project with a task that is due in Instant.EPOCH plus 1 day.
        ProjectBuilder projectBuilder = createProjectBuilder();
        Project project = projectBuilder.addTask(taskDueInFuture).build();
        
        // Should be equal to Instant.EPOCH.plus(1, ChronoUnit.DAYS)
        Instant taskDueDate = project.getTasks().get(0).getDueDate().get();
        assertEquals(futureDueDate, taskDueDate);

        // Verify that the current date is before the due date.
        Instant currentDate = Instant.EPOCH;
        assertTrue(currentDate.isBefore(taskDueDate));
        
        assertFalse(project.hasOverdueTasks(Clock.fixed(currentDate, ZoneId.of("Europe/Paris"))));
    }

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
