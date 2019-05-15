package com.xming.gymclubsystem.dto.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xming.gymclubsystem.domain.primary.UmUser;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserResource extends ResourceSupport {

    private final UmUser umUser;


    public UserResource(UmUser umUser) {
        this.umUser = umUser;
    }

    public UmUser getTrainer() {
        return umUser;
    }
}

