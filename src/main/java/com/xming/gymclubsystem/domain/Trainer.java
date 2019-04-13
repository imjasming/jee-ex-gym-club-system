package com.xming.gymclubsystem.domain;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
public class Trainer {
    @GeneratedValue
    @Id
    private Integer id;

    private String name;
    private int age;
    private String position;
    private String email;
    private String telephone;
    private double salary;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "trainers")
    @Fetch(FetchMode.SUBSELECT)
    private List<UmUser> umUsers = new LinkedList<>();


    @JoinColumn(name = "GYM_ID")
    @ManyToOne
    private Gym gym;


    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", salary=" + salary +
                '}';
    }
}
