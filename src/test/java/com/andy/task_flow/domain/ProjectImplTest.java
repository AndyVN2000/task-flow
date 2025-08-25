package com.andy.task_flow.domain;

import com.andy.task_flow.domain.entities.ProjectImpl;
import com.andy.task_flow.domain.entities.interfaces.Project;

public class ProjectImplTest extends ProjectContractTest {

    @Override
    protected Project createProject() {
        return ProjectImpl.of("Foo", "Bar");
    }
    
}
