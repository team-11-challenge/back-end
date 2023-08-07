package com.example.courseregistratioonbackend.domain.course.dto;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import lombok.Getter;

@Getter
public class CourseResponseDto {
    private final Long courseId;
    private final String collegeName;     // 대학
    private final String departmentName;  // 학과
    private final String majorName;       // 전공
    private final String sort;            // 이수구분
    private final Long subjectCd;         // 과목코드
    private final int division;           // 분반
    private final String subjectName;     // 교과목명
    private final int credit;             // 학점
    private final String professorName;   // 담당교수
    private final String timetable;       // 강의시간
    private final Long limitation;        // 제한인원
    private final Long numberOfBasket;    // 현재 예비 수강 신청 인원
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
