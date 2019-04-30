package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.primary.Trainer;
import com.xming.gymclubsystem.service.DataService;
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
@RestController
public class TrainerController {
    @Autowired
    private DataService dataService;

    @GetMapping("/trainers")
    public ResponseEntity getTrainerList(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNo") int pageNo
    ) {
        final Page<Trainer> trainerPage = dataService.pagingTrains(pageNo, pageSize);
        return trainerPage.hasContent() ? ResponseEntity.ok(trainerPage) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No more data");
    }

    @PostMapping("/user/{username}/add-trainer")
    public ResponseEntity addUserSTrainer(@PathVariable String username, @RequestParam("trainerId") int trainerId) {
        dataService.addUserTrainerByID(username, trainerId);
        final List<Trainer> trainerList = dataService.getUserTrainers(username);
        return ResponseEntity.ok().body(trainerList);
    }

    @GetMapping("/user/trainers")
    public ResponseEntity getUserTrainerList(@RequestAttribute("username") String username) {
        final List<Trainer> trainerList = dataService.getUserTrainers(username);

        return trainerList.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No more data") : ResponseEntity.ok(trainerList);
    }
}
