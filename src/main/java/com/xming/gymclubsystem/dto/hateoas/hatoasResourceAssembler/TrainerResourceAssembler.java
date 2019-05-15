package com.xming.gymclubsystem.dto.hateoas.hatoasResourceAssembler;

import com.xming.gymclubsystem.controller.HateoasController;
import com.xming.gymclubsystem.controller.TrainerController;
import com.xming.gymclubsystem.domain.primary.Trainer;
import com.xming.gymclubsystem.dto.hateoas.TrainResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class TrainerResourceAssembler extends ResourceAssemblerSupport<Trainer, TrainResource> {

    public TrainerResourceAssembler()
    {
        super(TrainerController.class, TrainResource.class);
    }

    @Override
    public TrainResource toResource(Trainer trainer) {
        TrainResource resource = createResourceWithId(trainer.getId(), trainer);
        return resource;
    }

    @Override
    protected TrainResource instantiateResource(Trainer entity) {
        return new TrainResource(entity);
    }
}
