package com.xming.gymclubsystem.dto.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xming.gymclubsystem.domain.secondary.Equipment;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EquipmentResource extends ResourceSupport {

    private final Equipment equipment;


    public EquipmentResource(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }
}

