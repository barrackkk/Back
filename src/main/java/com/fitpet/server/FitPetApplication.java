package com.fitpet.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FitPetApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitPetApplication.class, args);
    }

}
