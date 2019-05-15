package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.common.annotation.RateLimitAspect;
import com.xming.gymclubsystem.domain.primary.Gym;
import com.xming.gymclubsystem.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Xiaoming.
 * Created on 2019/04/22 22:31.
 */
@Api(tags = "GymController", value = "home gym list")
@RestController
public class GymController {
    @Autowired
    private DataService dataService;

    @ApiOperation("in home page, list gyms info")
    @RateLimitAspect(permitsPerSecond=10)
    @GetMapping("/gym-list")
    public ResponseEntity getGymList(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNo") int pageNo
    ) {
        final Page<Gym> gymPage = dataService.pagingGyms(pageNo, pageSize);
        return gymPage.hasContent() ? ResponseEntity.ok(gymPage) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No more data");
    }
}
