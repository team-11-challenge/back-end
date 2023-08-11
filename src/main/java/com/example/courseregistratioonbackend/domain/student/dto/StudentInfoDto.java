package com.example.courseregistratioonbackend.domain.student.dto;

import com.example.courseregistratioonbackend.domain.student.entity.Student;
import lombok.Getter;

@Getter
public class StudentInfoDto {
    private String studentNM;
    private String studentNum;
    private Integer possibleCredits;
    private Integer appliedCredits;

    public StudentInfoDto(Student student) {
        this.studentNM = student.getStudentNM();
        this.studentNum = student.getStudentNum();
        this.possibleCredits = student.getPossibleCredits();
        this.appliedCredits = student.getAppliedCredits();
    }
}
