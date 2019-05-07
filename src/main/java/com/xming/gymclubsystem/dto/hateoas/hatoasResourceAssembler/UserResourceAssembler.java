package com.xming.gymclubsystem.dto.hateoas.hatoasResourceAssembler;


import com.xming.gymclubsystem.controller.HateoasController;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.dto.hateoas.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class UserResourceAssembler extends ResourceAssemblerSupport<UmUser, UserResource> {

    public UserResourceAssembler()
    {
        super(HateoasController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(UmUser umUser) {
        UserResource resource = createResourceWithId(umUser.getId(), umUser);
        return resource;
    }

    @Override
    protected UserResource instantiateResource(UmUser entity) {
        return new UserResource(entity);
    }
}
