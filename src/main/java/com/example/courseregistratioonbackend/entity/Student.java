package com.example.courseregistratioonbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "student_num", unique = true)
    private Long studentNum;

    @Column(name = "student_nm")
    private String studentNM;

    @Column
    private String password;

    @Column(name = "possible_credits")
    private Integer possibleCredits;
}
