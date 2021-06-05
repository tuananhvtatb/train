package com.edu.hutech.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Data
@Entity
@Table(name = "trainer")
public class Trainer extends BaseEntity implements Serializable {

     @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
     private List<Course> courseList;

     @Column(name = "name")
     private String name;

     @Column(name = "email")
     private String email;

     @Column(name = "tel_phone")
     private String telPhone;

     public User getUser() {
          return user;
     }

     public void setUser(User user) {
          this.user = user;
     }

     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "user_id",  referencedColumnName = "id")
     private User user;

//    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<TrainingObjective> trainingObjectives;



    // public Trainer(List<Course> courseList, List<TrainingObjective> trainingObjectives) {
    //     this.courseList = courseList;
    //     this.trainingObjectives = trainingObjectives;
    // }

    // public List<Course> getCourseList() {
    //     return courseList;
    // }

    // public void setCourseList(List<Course> courseList) {
    //     for(Course child : courseList) {
    //         child.setTrainer(this);
    //     }
    //     this.courseList = courseList;
    // }


}
