package com.training.web.rest.errors;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class InvalidTaskIdException extends BadRequestAlertException{

    private static final long serialVersionUID = 1L;

    public InvalidTaskIdException() {
        super("Cannot change task ID", "taskManagement", "invalidTaskId");
    }
}
