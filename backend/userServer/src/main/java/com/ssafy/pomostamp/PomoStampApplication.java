package com.ssafy.pomostamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PomoStampApplication {

    public static void main(String[] args) {
        SpringApplication.run(PomoStampApplication.class, args);
    }

}
