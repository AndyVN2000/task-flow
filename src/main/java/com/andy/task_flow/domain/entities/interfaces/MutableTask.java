package com.andy.task_flow.domain.entities.interfaces;

import java.time.Clock;
import java.time.Instant;

public interface MutableTask extends Task {
    
    public void rename(String newName);

    public void changeDescription(String newDescription);

    public void setDueDate(Instant dueDate);

    public void clearDueDate();

    /**
     * Mark the status of Task as being in progress
     * CREATED -> IN_PROGRESS
     */
    public void start();

    /**
     * Mark the status of task being complete and save the date in which it was completed.
     * IN_PROGRESS -> COMPLETED
     * @param clock to keep track of when the task was complete.
     */
    public void complete(Clock clock);

    /**
     * Reopen the task to be in progress again due to hotfixing.
     * COMPLETED -> IN_PROGRESS
     */
    public void reopen();

}
