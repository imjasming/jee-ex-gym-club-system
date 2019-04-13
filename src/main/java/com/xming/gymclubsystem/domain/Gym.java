package com.xming.gymclubsystem.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Gym {
    @Id
    @GeneratedValue
    private Integer id;
    private String gymName;
    private String location;

}
