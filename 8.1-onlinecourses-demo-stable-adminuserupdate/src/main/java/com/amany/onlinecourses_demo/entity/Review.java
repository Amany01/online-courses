package com.amany.onlinecourses_demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "review")
public class Review {
    // define & annotate fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "comment")
    @NotBlank
    private String comment;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
               CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "course_id")
    private Course course;

    // define constructors
    public Review () {}
    public Review (String comment) {
        this.comment = comment;
    }

    // define getters/setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // define toString

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                '}';
    }
}

