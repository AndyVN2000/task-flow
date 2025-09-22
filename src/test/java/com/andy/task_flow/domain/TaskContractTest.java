package com.andy.task_flow.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.interfaces.Task;

public abstract class TaskContractTest {

    protected abstract Task createTask();

    @Test
    public void idShouldBeNonNull() {
        Task task = createTask();
        assertNotNull(task.getId());
    }

}
