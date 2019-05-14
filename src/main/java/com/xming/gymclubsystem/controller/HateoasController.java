package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.common.annotation.RateLimitAspect;
import com.xming.gymclubsystem.dto.hateoas.Greeting;
import com.xming.gymclubsystem.dto.hateoas.GymResource;
import com.xming.gymclubsystem.dto.hateoas.hatoasResourceAssembler.GymResourceAssembler;
import com.xming.gymclubsystem.service.DataService;
import com.xming.gymclubsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class HateoasController {


    @Autowired
    private DataService dataService;
    @Autowired
    private UserService userService;


    private static final String TEMPLATE = "Hello, %s!";

    @RateLimitAspect(permitsPerSecond=10)
    @RequestMapping("/greeting")
    public HttpEntity<GymResource> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        GymResource gymResource = new GymResourceAssembler().toResource(dataService.getGym(name));
        gymResource.add(linkTo(methodOn(HateoasController.class).greeting(name)).withSelfRel());

        return new ResponseEntity<GymResource>(gymResource, HttpStatus.OK);
    }
}
