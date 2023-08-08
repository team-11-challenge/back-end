package com.example.courseregistratioonbackend.global.parsing.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @Column(nullable = false, name = "subject_nm", unique = true)
    private String subjectNM; // 과목명

    @Column(nullable = false, name = "subject_cd", unique = true)
    private Long subjectCD; // 과목코드
}
