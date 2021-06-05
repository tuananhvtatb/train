package com.edu.hutech.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "open_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String openDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String endDate;

    @Column(name = "duration")
    private int duration;

    @Column(name = "note")
    private String note;

    @Column(name = "plan_count")
    private int planCount;

    @Column(name = "current_count")
    private int currCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.EAGER)
    private List<TraineeCourse> traineeCourses = new ArrayList<>();

    public void addTraineeCourses(TraineeCourse _traineeCourse) {
        _traineeCourse.setCourse(this);
        traineeCourses.add(_traineeCourse);
    }

//    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Issue> issues;


}
