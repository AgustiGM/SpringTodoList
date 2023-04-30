package com.techtest.test.entities;

import jakarta.persistence.*;

@Entity
public class TODO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TODO(String title, User user) {
        this.title = title;
        this.user = user;
    }

    public TODO(){}

    @Column(name = "title")
    private String title;

    @Column(name = "completed")
    private boolean completed = false;

    @ManyToOne
    private User user;

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
