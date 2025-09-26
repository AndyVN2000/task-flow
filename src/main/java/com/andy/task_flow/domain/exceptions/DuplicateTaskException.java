package com.andy.task_flow.domain.exceptions;

public class DuplicateTaskException extends RuntimeException {

    public DuplicateTaskException(String errorMessage) {
        super(errorMessage);
    }

}
