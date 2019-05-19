package com.xming.gymclubsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

//@EnableJpaRepositories("com.xming.gymclubsystem.repository")
@EnableAsync
@SpringBootApplication
public class GymClubSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymClubSystemApplication.class, args);
    }

}
