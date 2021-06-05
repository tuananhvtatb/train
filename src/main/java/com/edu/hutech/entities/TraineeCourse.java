package com.edu.hutech.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "trainee_course")
public class TraineeCourse extends BaseEntity{

    @Column(name = "code")
    private String code;

    @Column(name = "score")
    private Double score;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "course_id")
    private Course course;
}
