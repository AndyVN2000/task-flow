package com.andy.task_flow.application.exceptions;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
