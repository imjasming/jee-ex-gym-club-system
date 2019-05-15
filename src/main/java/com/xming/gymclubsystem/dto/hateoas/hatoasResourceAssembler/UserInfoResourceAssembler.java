package com.xming.gymclubsystem.dto.hateoas.hatoasResourceAssembler;


import com.xming.gymclubsystem.controller.HateoasController;
import com.xming.gymclubsystem.controller.UserInfoController;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.domain.secondary.UserInfo;
import com.xming.gymclubsystem.dto.hateoas.UserInfoResource;
import com.xming.gymclubsystem.dto.hateoas.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class UserInfoResourceAssembler extends ResourceAssemblerSupport<UserInfo, UserInfoResource> {

    public UserInfoResourceAssembler()
    {
        super(UserInfoController.class, UserInfoResource.class);
    }

    @Override
    public UserInfoResource toResource(UserInfo umUser) {
        UserInfoResource resource = createResourceWithId(umUser.getId(), umUser);
        return resource;
    }

    @Override
    protected UserInfoResource instantiateResource(UserInfo entity) {
        return new UserInfoResource(entity);
    }
}
