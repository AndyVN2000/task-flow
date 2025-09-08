package com.andy.task_flow.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.andy.task_flow.domain.entities.interfaces.Project;

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

}
