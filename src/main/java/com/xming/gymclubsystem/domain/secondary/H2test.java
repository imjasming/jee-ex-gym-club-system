package com.xming.gymclubsystem.domain.secondary;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class H2test implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String intro;

    @Override
    public String toString() {
        return "Gym{" +
                "id=" + id +
                ", intro='" + intro + '\'' +
                '}';
    }
}
