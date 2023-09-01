package com.example.courseregistratioonbackend.domain.course.entity;

import com.example.courseregistratioonbackend.global.parsing.entity.Belong;
import com.example.courseregistratioonbackend.global.parsing.entity.Professor;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
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
    @Builder.Default
    private Long basket = 0L; // 수강 예비신청 인원

    @Column(nullable = false)
    @Builder.Default
    private Long current = 0L; // 수강 신청 인원

    @Column(nullable = false, name = "course_year")
    private int courseYear; // 연도

    @Column(nullable = false)
    private int semester; // 학기

    @ManyToOne(cascade = CascadeType.MERGE, fetch = LAZY)
    @JoinColumn(name = "belong_id")
    private Belong belong; // 소속

    @ManyToOne(cascade = CascadeType.MERGE, fetch = LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject; // 교과목

    @ManyToOne(cascade = CascadeType.MERGE, fetch = LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor; // 교수
    public void addToBasketNumber() {
      this.basket = basket + 1;
    }

    public void removeFromBasketNumber() {
      this.basket = basket - 1;
    }

    public void addRegistration() {
        this.current++;
    }

    public void deleteRegistration() {
        this.current--;
    }

    public void setCurrent(Long leftSeatsInReds){
        this.current = leftSeatsInReds;
    }

}



