package com.edu.hutech.entities;

import com.edu.hutech.utils.data.TypeAttendance;
import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Data
@Entity
@Table(name="attendance")
public class Attendance extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_person", nullable = false, referencedColumnName = "id")
    private User user;

    @Column(name="type_attendance")
    private TypeAttendance typeAttendance;

    @Column(name="date")
    private Date date;

    @Column(name="note")
    private String note;

}
