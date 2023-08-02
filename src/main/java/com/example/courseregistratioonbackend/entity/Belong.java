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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "college_id")
    private College college;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "depart_id")
    private Department department;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "major_id")
    private Major major;
}
