package com.edu.hutech.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "score")
public class Score extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private float value;

}
