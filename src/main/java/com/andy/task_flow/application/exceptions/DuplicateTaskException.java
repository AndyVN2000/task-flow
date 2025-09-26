package com.andy.task_flow.application.exceptions;

public class DuplicateTaskException extends RuntimeException {

    public DuplicateTaskException(String errorMessage) {
        super(errorMessage);
    }

}
