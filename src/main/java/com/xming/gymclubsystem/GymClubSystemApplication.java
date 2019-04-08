package com.xming.gymclubsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.xming.gymclubsystem.repository")
@SpringBootApplication
public class GymClubSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymClubSystemApplication.class, args);
    }

}
