package com.example.courseregistratioonbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sort; // 구분

    @Column(nullable = false)
    private int division; // 분반

    @Column(nullable = false)
    private int credit; // 학점

    @Column(nullable = false)
    private String timetable; // 시간표

    @Column(nullable = false)
    private Long limitation; // 수강정원

    @Column(nullable = false)
    private Long basket; // 수강 예비신청 인원

    @Column(nullable = false)
    private Long current; // 수강 신청 인원

    @Column(nullable = false)
    private int yearCourse; // 연도

    @Column(nullable = false)
    private int semester; // 학기

    @ManyToOne
    @JoinColumn(name = "majorCD")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "subjectCD")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
}



