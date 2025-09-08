package com.andy.task_flow.domain;

import com.andy.task_flow.domain.entities.ArchivedProject;
import com.andy.task_flow.domain.entities.interfaces.Project;

public class ArchivedProjectTest extends ProjectContractTest {

    @Override
    protected Project createProject() {
        return ArchivedProject.of("Foo", "Bar");
    }
    
}
