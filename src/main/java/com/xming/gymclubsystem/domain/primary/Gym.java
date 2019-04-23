package com.xming.gymclubsystem.domain.primary;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Gym implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String gymName;
    private String location;
    private String intro;

    @Override
    public String toString() {
        return "Gym{" +
                "id=" + id +
                ", gymName='" + gymName + '\'' +
                ", location='" + location + '\'' +
                ", intro='" + intro + '\'' +
                '}';
    }
}
