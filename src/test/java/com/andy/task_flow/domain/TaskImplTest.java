package com.andy.task_flow.domain;

import java.time.Instant;
import java.util.Optional;

import com.andy.task_flow.domain.entities.TaskImpl;
import com.andy.task_flow.domain.entities.interfaces.Project;
import com.andy.task_flow.domain.entities.interfaces.Task;
import com.andy.task_flow.fixtures.doubles.ProjectStub;

public class TaskImplTest extends TaskContractTest {

    @Override
    protected Task createTask() {
        Project project = new ProjectStub();
        return TaskImpl.of("Foo", "Bar", project, Optional.of(Instant.EPOCH));
    }

}
