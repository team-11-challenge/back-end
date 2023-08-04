package com.example.courseregistratioonbackend.global.parsing.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 대학
 **/
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "college_id")
    private Long id;

    @Column(nullable = false, unique = true, name = "college_nm")
    private String collegeNM;   // 대학명

    @Column(nullable = false, name = "college_cd")
    private Long collegeCD;   // 대학코드
}
