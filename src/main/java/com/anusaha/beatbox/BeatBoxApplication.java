package com.anusaha.beatbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class BeatBoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeatBoxApplication.class, args);
    }
}