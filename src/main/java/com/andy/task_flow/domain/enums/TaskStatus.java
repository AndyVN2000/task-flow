package com.andy.task_flow.domain.enums;

public enum TaskStatus {
    TODO,            // Default status. Task has not be started yet.
    IN_PROGRESS,     // Work on task has started.
    BLOCKED,         // Task is currently blocked (waiting for something).
    IN_REVIEW,       // Task is completed, but under peer or QA review.
    DONE,            // Task has been completed and verified.
    CANCELLED        // Task was cancelled.
}
