package com.fitpet.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FitPetApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitPetApplication.class, args);
    }

}