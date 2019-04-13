package com.xming.gymclubsystem.domain;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class Trainer {

    private Integer id;
    private String name;
    private int age;
    private String position;
    private String email;
    private String telephone;
    private double salary;


}
