package com.example.courseregistratioonbackend.domain.registration.dto;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.registration.entity.Registration;
import com.example.courseregistratioonbackend.global.parsing.entity.Belong;
import com.example.courseregistratioonbackend.global.parsing.entity.Department;
import com.example.courseregistratioonbackend.global.parsing.entity.Major;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RegistrationDto {
    private Long registrationId;
    private String collegeNM;   // 대학명
    private String departNM;    // 학과명
    private String majorNM;     // 학과명
    private String sort;        // 구분
    private Long subjectCD;     // 과목코드
    private int division;       // 분반
    private String subjectNM;   // 과목명
    private int credit;         // 학점
    private String professorNM; // 교수명
    private String timetable;   // 시간표
    private Long limitation; // 수강정원
    private Long current; // 수강 신청 인원

    public RegistrationDto(Registration registration) {
        Course course = registration.getCourse();
        Belong belong = course.getBelong();
        Subject subject = course.getSubject();
        Department department = belong.getDepartment();
        Major major = belong.getMajor();

        this.registrationId = registration.getId();
        this.collegeNM = belong.getCollege().getCollegeNM();
        this.departNM = (department != null) ? department.getDepartNM() : null;
        this.majorNM = (major != null) ? major.getMajorNM() : null;
        this.sort = course.getSort();
        this.subjectCD = subject.getSubjectCD();
        this.division = course.getDivision();
        this.subjectNM = subject.getSubjectNM();
        this.credit = course.getCredit();
        this.professorNM = course.getProfessor().getProfessorNM();
        this.timetable = course.getTimetable();
        this.limitation = course.getLimitation();
        this.current = course.getCurrent();
    }
}
