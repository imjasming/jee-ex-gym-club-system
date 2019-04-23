package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.Gym;
import com.xming.gymclubsystem.domain.Trainer;
import com.xming.gymclubsystem.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/gym-list")
    public ResponseEntity<Page<Gym>> getGymList(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNO") int pageNo
    ) {
        final Page<Gym> gymPage = dataService.pagingGyms(pageNo, pageSize);

        return gymPage != null ? ResponseEntity.ok(gymPage) : ResponseEntity.notFound().build();
    }

    /*@GetMapping("/trainers/{username}")
    public ResponseEntity<Page<Trainer>> getUserTrainerList(@PathVariable String username){

    }*/
}
