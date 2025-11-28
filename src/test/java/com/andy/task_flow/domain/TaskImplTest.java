package com.andy.task_flow.domain;

import java.time.Instant;
import java.util.Optional;

import com.andy.task_flow.domain.entities.TaskImpl;
import com.andy.task_flow.domain.entities.base.AbstractProject;
import com.andy.task_flow.domain.entities.base.AbstractTask;
import com.andy.task_flow.fixtures.doubles.ProjectStub;

public class TaskImplTest extends TaskContractTest {

    @Override
    protected AbstractTask createTask() {
        AbstractProject project = new ProjectStub();
        return TaskImpl.of("Foo", "Bar", project, Optional.of(Instant.EPOCH));
    }

}
