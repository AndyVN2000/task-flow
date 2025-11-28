package com.andy.task_flow.domain;

import java.time.Instant;
import java.util.Optional;

import com.andy.task_flow.domain.entities.ArchivedTask;
import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.fixtures.doubles.ProjectStub;
import com.andy.task_flow.domain.entities.base.AbstractTask;

public class ArchivedTaskTest extends TaskContractTest {

    @Override
    protected AbstractTask createTask() {
        AbstractProject project = new ProjectStub();
        return ArchivedTask.of("Foo", "Bar", project, Optional.of(Instant.EPOCH));
    }

}
