package com.training.web.rest.vm;

public class TaskStateVM {

    private String state;

    public TaskStateVM(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TaskStateVM{" +
            "state='" + state + '\'' +
            '}';
    }
}
