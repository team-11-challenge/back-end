package com.example.courseregistratioonbackend.domain.course.dto;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "강의 정보")
@Getter
public class CourseResponseDto {
    private final Long courseId;

    @Schema(description = "대학명")
    private final String collegeName;     // 대학

    @Schema(description = "학과명")
    private final String departmentName;  // 학과

    @Schema(description = "전공명")
    private final String majorName;       // 전공

    @Schema(description = "이수구분",
            allowableValues = {"기초교양", "균형교양", "학문기초", "교직", "자유선택", "전공선택", "전공필수"})
    private final String sort;            // 이수구분

    @Schema(description = "과목 코드")
    private final Long subjectCd;         // 과목코드

    @Schema(description = "분반")
    private final int division;           // 분반

    @Schema(description = "과목명")
    private final String subjectName;     // 교과목명

    @Schema(description = "학점")
    private final int credit;             // 학점

    @Schema(description = "담당교수")
    private final String professorName;   // 담당교수

    @Schema(description = "강의시간")
    private final String timetable;       // 강의시간

    @Schema(description = "제한인원")
    private final Long limitation;        // 제한인원

    @Schema(description = "현재 예비 수강 신청 인원(장바구니)")
    private final Long numberOfBasket;    // 현재 예비 수강 신청 인원

    @Schema(description = "현재 수강 신청 인원")
    private final Long numberOfCurrent;   // 현재 수강 신청 인원

    public CourseResponseDto(Course course) {
        this.courseId = course.getId();
        this.collegeName = course.getBelong().getCollege().getCollegeNM();
        this.departmentName = course.getBelong().getDepartment() == null ?
                null : course.getBelong().getDepartment().getDepartNM();

        this.majorName = course.getBelong().getMajor() == null ?
                null : course.getBelong().getMajor().getMajorNM();

        this.sort = course.getSort();
        this.subjectCd = course.getSubject().getSubjectCD();
        this.division = course.getDivision();
        this.subjectName = course.getSubject().getSubjectNM();
        this.credit = course.getCredit();
        this.professorName = course.getProfessor().getProfessorNM();
        this.timetable = course.getTimetable();
        this.limitation = course.getLimitation();
        this.numberOfBasket = course.getBasket();
        this.numberOfCurrent = course.getCurrent();
    }
}
