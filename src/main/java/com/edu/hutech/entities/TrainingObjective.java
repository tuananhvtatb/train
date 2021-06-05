package com.edu.hutech.entities;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "training_objective")
public class TrainingObjective extends BaseEntity{


    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_trainer", referencedColumnName = "id")
//    private Trainer trainer;

//    @OneToMany(mappedBy = "training_objective", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<FeedBack> feedBacks;

//    @OneToMany(mappedBy = "primaryKey.training_objective", cascade = CascadeType.ALL)
//    private Set<Score> scores = new HashSet<Score>();

}
