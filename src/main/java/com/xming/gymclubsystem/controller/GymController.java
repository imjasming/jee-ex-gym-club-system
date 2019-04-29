package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.primary.Gym;
import com.xming.gymclubsystem.domain.primary.Trainer;
import com.xming.gymclubsystem.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2019/04/22 22:31.
 */
@RestController
public class GymController {
    @Autowired
    private DataService dataService;

    @GetMapping("/trainers")
    public ResponseEntity<Page<Trainer>> getTrainerList(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNo") int pageNo
    ) {
        final Page<Trainer> trainerPage = dataService.pagingTrains(pageNo, pageSize);
        return trainerPage.hasContent() ? ResponseEntity.ok(trainerPage) : ResponseEntity.notFound().build();
    }

    @PostMapping("/user/{username}/add-trainer")
    public ResponseEntity addUserSTrainer(@PathVariable String username, @RequestParam("trainerId") int trainerId) {
        dataService.addUserTrainerByID(username, trainerId);
        final List<Trainer> trainerList = dataService.getUserTrainers(username);
        return ResponseEntity.ok().body(trainerList);
    }

    @GetMapping("/user/trainers")
    public ResponseEntity<List<Trainer>> getUserTrainerList(@RequestAttribute("username") String username) {
        final List<Trainer> trainerList = dataService.getUserTrainers(username);

        return trainerList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(trainerList);
    }

    @GetMapping("/gym-list")
    public ResponseEntity<Page<Gym>> getGymList(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNo") int pageNo
    ) {
        final Page<Gym> gymPage = dataService.pagingGyms(pageNo, pageSize);
        return gymPage.hasContent() ? ResponseEntity.ok(gymPage) : ResponseEntity.notFound().build();
    }
}
