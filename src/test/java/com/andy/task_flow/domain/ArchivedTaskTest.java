package com.andy.task_flow.domain;

import java.time.Instant;
import java.util.Optional;

import com.andy.task_flow.domain.entities.ArchivedTask;
import com.andy.task_flow.fixtures.doubles.ProjectStub;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;

public class ArchivedTaskTest extends TaskContractTest {

    @Override
    protected Task createTask() {
        Project project = new ProjectStub();
        return ArchivedTask.of("Foo", "Bar", project, Optional.of(Instant.EPOCH));
    }

}
