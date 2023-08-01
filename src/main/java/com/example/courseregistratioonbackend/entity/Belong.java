package com.example.courseregistratioonbackend.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 소속
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Belong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "belong_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @ManyToOne
    @JoinColumn(name = "depart_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;
}
