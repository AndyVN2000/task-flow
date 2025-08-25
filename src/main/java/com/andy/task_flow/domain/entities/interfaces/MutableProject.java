package com.andy.task_flow.domain.entities.interfaces;

import java.time.Clock;
import java.util.UUID;

import com.andy.task_flow.domain.entities.ArchivedProject;
import com.andy.task_flow.domain.entities.Task;

public interface MutableProject extends Project {

    /**
     * First validates the new name and throws `IllegalArgumentException` if validation fails.
     * New name is valid if it is non-null, non-empty and stays within character limits.
     * @param newName
     */
    public void rename(String newName);

    /**
     * 
     * @param newDescription may be null or blank. Can be used to "clear" description.
     */
    public void changeDescription(String newDescription);

    /**
     * If null or adding a duplicate task, throws `IllegalArgumentException`.
     * @param task must not be null and has unique ID within the project.
     */
    public void addTask(Task task);

    /**
     * Dev note: I prefer that it throws some `IllegalArgumentException` if the task ID is not found.
     *           Getting feedback on software behavior is good.
     * @param taskId
     */
    public void removeTask(UUID taskId);
    
    /**
     * Archives the project so it becomes immutable.
     * @param archivedBy Who archived the project.
     * @param clock
     * @return Returns the archived project that is immutable.
     */
    public ArchivedProject archive(String archivedBy, Clock clock);

}
