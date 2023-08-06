package com.example.courseregistratioonbackend.global.parsing.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private Long id;

    @Column(nullable = false, unique = true, name = "major_nm")
    private String majorNM;   // 학과명

    @Column(nullable = false, name = "major_cd")
    private Long majorCD;   // 학과코드
}
