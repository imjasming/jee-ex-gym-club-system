package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.common.annotation.RateLimitAspect;
import com.xming.gymclubsystem.domain.primary.Trainer;
import com.xming.gymclubsystem.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2019/04/29 20:20.
 */
@Api(tags = "TrainerController")
@RestController
public class TrainerController {
    @Autowired
    private DataService dataService;

    @RateLimitAspect(permitsPerSecond=10)
    @ApiOperation("show trainer's list in home page")
    @GetMapping("/trainers")
    public ResponseEntity getTrainerList(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNo") int pageNo
    ) {
        final Page<Trainer> trainerPage = dataService.pagingTrains(pageNo, pageSize);
        return trainerPage.hasContent() ? ResponseEntity.ok(trainerPage) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No more data");
    }

    @RateLimitAspect(permitsPerSecond=10)
    @ApiOperation("user add a trainer")
    @PostMapping("/user/{username}/trainer")
    public ResponseEntity addUserSTrainer(@PathVariable String username, @RequestParam("trainerId") int trainerId) {
        dataService.addUserTrainerByID(username, trainerId);
        final List<Trainer> trainerList = dataService.getUserTrainers(username);
        return ResponseEntity.ok().body(trainerList);
    }

    @RateLimitAspect(permitsPerSecond=10)
    @ApiOperation("get user's trainer list")
    @GetMapping("/user/{username}/trainers")
    public ResponseEntity getUserTrainerList(@PathVariable("username") String username) {
        final List<Trainer> trainerList = dataService.getUserTrainers(username);

        return trainerList.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No more data") : ResponseEntity.ok(trainerList);
    }
}
