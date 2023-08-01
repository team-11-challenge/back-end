package com.example.courseregistratioonbackend.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 학과
 **/
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "depart_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String departNM;   // 학과명

    @Column(nullable = false)
    private Long departCD;   // 학과코드
}

