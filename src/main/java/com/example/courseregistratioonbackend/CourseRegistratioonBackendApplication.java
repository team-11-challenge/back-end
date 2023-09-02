package com.example.courseregistratioonbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CourseRegistratioonBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseRegistratioonBackendApplication.class, args);
    }

}
