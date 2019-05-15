package com.xming.gymclubsystem.dto.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xming.gymclubsystem.domain.primary.Gym;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
public class GymResource extends ResourceSupport {

    private final Gym gym;


    public GymResource(Gym gym) {
        this.gym = gym;
    }

    public Gym getGym() {
        return gym;
    }
}

