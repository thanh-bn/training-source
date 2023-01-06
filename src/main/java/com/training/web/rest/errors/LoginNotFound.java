package com.training.web.rest.errors;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class LoginNotFound extends BadRequestAlertException{

    private static final long serialVersionUID = 1L;

    public LoginNotFound() {
        super(ErrorConstants.LOGIN_NOT_FOUND_TYPE, "Current user login not found", "taskManagement", "unauthenticated");
    }
}
