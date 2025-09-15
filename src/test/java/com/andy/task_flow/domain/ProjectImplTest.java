package com.andy.task_flow.domain;

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
import com.andy.task_flow.fixtures.builders.ProjectImplBuilder;

public class ProjectImplTest extends ProjectContractTest {

    private ProjectImpl project;

    @Override
    protected Project createProject() {
        return ProjectImpl.of("Foo", "Bar");
    }

    @Override
    protected ProjectBuilder createBuilder() {
        return new ProjectImplBuilder();
    }

    @BeforeEach
    public void setUp() {
        project = (ProjectImpl) createProject();
    }
    
    @Test
    public void projectCanBeArchived() {
        Project archivedProject = project.archive("John Doe", Clock.fixed(Instant.EPOCH, ZoneId.of("Europe/Paris")));
        assertTrue(archivedProject instanceof ArchivedProject);
    }

}
