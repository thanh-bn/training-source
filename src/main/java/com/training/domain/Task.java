package com.training.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tasks")
public class Task extends AbstractAuditingEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String title;
    @Column(length = 500)
    private String content;
    @Column(length = 50)
    private String state;
    private Instant deadline;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    public Task() {
    }

    public Task(Long id, String title, String content, String state, Instant deadline, User author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.state = state;
        this.deadline = deadline;
        this.author = author;
    }

    @Override
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
