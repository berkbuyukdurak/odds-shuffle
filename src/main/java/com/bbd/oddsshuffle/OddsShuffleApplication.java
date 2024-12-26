package com.bbd.oddsshuffle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OddsShuffleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OddsShuffleApplication.class, args);
    }

}
