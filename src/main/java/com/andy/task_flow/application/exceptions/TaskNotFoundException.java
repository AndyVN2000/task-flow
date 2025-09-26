package com.andy.task_flow.application.exceptions;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
