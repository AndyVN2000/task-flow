package com.andy.task_flow.domain;

/**
 * This testing pattern is based on: https://www.baeldung.com/java-junit-verify-interface-contract
 * The advantage of this pattern is that it avoids duplication of test cases on methods
 * that are common for both ProjectImpl and ArchivedProject.
 * Example: both classes have a getName() method. It has the same behavior in both classes.
 *          If we simply wrote separate test classes for each implementing class,
 *          then we would end up duplicating the test case that getName() should return a string.
 */

public abstract class ProjectContractTest {
    
}
