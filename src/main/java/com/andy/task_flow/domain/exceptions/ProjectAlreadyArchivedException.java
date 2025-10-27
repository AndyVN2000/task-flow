package com.andy.task_flow.domain.exceptions;

public class ProjectAlreadyArchivedException extends RuntimeException {

    public ProjectAlreadyArchivedException(String errorMessage) {
        super(errorMessage);
    }

}
