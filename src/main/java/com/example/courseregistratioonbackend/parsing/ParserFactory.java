package com.example.courseregistratioonbackend.parsing;

import com.example.courseregistratioonbackend.entity.Course;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserFactory {
    @Bean
    public ReadLineContext<Course> courseReadLineContextest(){
        return new ReadLineContext<Course>(new CourseParser());
    }
}