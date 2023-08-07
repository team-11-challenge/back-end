package com.example.courseregistratioonbackend.domain.student.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TimetableResponseDto {
    private String courseNM;
    private List<String[]> timetable;

    public TimetableResponseDto(String courseNM, List<String[]> timetable){
        this.courseNM = courseNM;
        this.timetable = timetable;
    }
}
