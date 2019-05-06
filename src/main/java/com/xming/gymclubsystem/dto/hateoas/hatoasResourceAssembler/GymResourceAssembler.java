package com.xming.gymclubsystem.dto.hateoas.hatoasResourceAssembler;

import com.xming.gymclubsystem.controller.HateoasController;
import com.xming.gymclubsystem.domain.primary.Gym;
import com.xming.gymclubsystem.dto.hateoas.GymResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class GymResourceAssembler extends ResourceAssemblerSupport<Gym, GymResource>{

    public GymResourceAssembler()
    {
        super(HateoasController.class, GymResource.class);
    }

    @Override
    public GymResource toResource(Gym gym) {
        GymResource resource = createResourceWithId(gym.getId(), gym);
        return resource;
    }

    @Override
    protected GymResource instantiateResource(Gym entity) {
        return new GymResource(entity);
    }
}
