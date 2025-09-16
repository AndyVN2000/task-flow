package com.andy.task_flow.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.ArchivedProject;
import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.ProjectBuilder;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.fixtures.builders.ProjectImplBuilder;
import com.andy.task_flow.fixtures.builders.TaskBuilder;

public class ProjectImplTest extends ProjectContractTest {

    private ProjectImpl project;
    private TaskBuilder taskBuilder;

    @Override
    protected Project createProject() {
        return ProjectImpl.of("Foo", "Bar");
    }

    @Override
    protected ProjectBuilder createProjectBuilder() {
        return new ProjectImplBuilder();
    }

    @BeforeEach
    public void setUp() {
        project = (ProjectImpl) createProject();
        taskBuilder = createTaskBuilder();
    }
    
    @Test
    public void projectCanBeArchived() {
        Project archivedProject = project.archive("John Doe", Clock.fixed(Instant.EPOCH, ZoneId.of("Europe/Paris")));
        assertTrue(archivedProject instanceof ArchivedProject);
        assertEquals(archivedProject.getId(), project.getId());
        assertEquals(archivedProject.getName(), project.getName());
        assertEquals(archivedProject.getDescription(), project.getDescription());
        assertEquals(archivedProject.getCreatedAt(), project.getCreatedAt());
        assertEquals(archivedProject.getTasks(), project.getTasks());
    }

    @Test
    public void projectCanBeRenamed() {
        String oldName = project.getName();
        project.rename("baz");
        assertNotEquals(oldName, project.getName());
    }

    @Test
    public void projectCanGetNewDescription() {
        String oldDescription = project.getDescription();
        project.changeDescription("hello world");
        assertNotEquals(oldDescription, project.getDescription());
    }

    @Test
    public void shouldAddTaskToProject() {
        int oldTaskCount = project.getTasks().size();
        Task newTask = taskBuilder.build();
        project.addTask(newTask);
        int newTaskCount = project.getTasks().size();
        assertNotEquals(newTaskCount, oldTaskCount);
        assertEquals(newTaskCount - 1, oldTaskCount);
    }

}
