package com.example.courseregistratioonbackend.global.parsing.entity;

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

    @Column(nullable = false, unique = true, name = "depart_nm")
    private String departNM;   // 학과명

    @Column(nullable = false, name = "depart_cd")
    private Long departCD;   // 학과코드
}

