package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.primary.Gym;
import com.xming.gymclubsystem.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/gym-list")
    public ResponseEntity getGymList(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNo") int pageNo
    ) {
        final Page<Gym> gymPage = dataService.pagingGyms(pageNo, pageSize);
        return gymPage.hasContent() ? ResponseEntity.ok(gymPage) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No more data");
    }
}
