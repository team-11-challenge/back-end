package com.example.courseregistratioonbackend.domain.course.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@ToString
public class CourseRequestDto {
    private int courseYear;
    private int semester;
    private Long subjectCd;
    private String depart;
    private String sort;
}
