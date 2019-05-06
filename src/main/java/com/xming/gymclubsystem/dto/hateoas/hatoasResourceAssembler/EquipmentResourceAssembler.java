package com.xming.gymclubsystem.dto.hateoas.hatoasResourceAssembler;

import com.xming.gymclubsystem.controller.HateoasController;
import com.xming.gymclubsystem.domain.secondary.Equipment;
import com.xming.gymclubsystem.dto.hateoas.EquipmentResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class EquipmentResourceAssembler extends ResourceAssemblerSupport<Equipment, EquipmentResource> {

    public EquipmentResourceAssembler() {
        super(HateoasController.class, EquipmentResource.class);
    }

    @Override
    public EquipmentResource toResource(Equipment equipment) {
        EquipmentResource resource = createResourceWithId(equipment.getId(), equipment);
        return resource;
    }

    @Override
    protected EquipmentResource instantiateResource(Equipment entity) {
        return new EquipmentResource(entity);
    }
}
