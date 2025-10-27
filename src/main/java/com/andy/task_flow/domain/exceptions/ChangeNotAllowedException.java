package com.andy.task_flow.domain.exceptions;

public class ChangeNotAllowedException extends RuntimeException {

    public ChangeNotAllowedException(String errorMessage) {
        super(errorMessage);
    }

}
