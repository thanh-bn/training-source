package com.training.service.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

public class TaskDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;
    @NotBlank
    @Size(min = 1, max = 200)
    private String title;
    @Size(max = 500)
    private String content;
    @Size(max = 50)
    private String state;
    private Instant deadline;
    private UserDTO author;

    public TaskDTO() {
    }

    public TaskDTO(Long id, String title, String content, String state, Instant deadline, UserDTO author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = state;
        this.deadline = deadline;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", state='" + state + '\'' +
            ", deadline=" + deadline +
            ", author=" + author +
            '}';
    }
}
