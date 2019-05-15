package com.xming.gymclubsystem.dto.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xming.gymclubsystem.domain.primary.Trainer;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrainResource extends ResourceSupport {

    private final Trainer trainer;

    public TrainResource(Trainer trainer) {
        this.trainer = trainer;
    }

    public Trainer getTrainer() {
        return trainer;
    }
}

