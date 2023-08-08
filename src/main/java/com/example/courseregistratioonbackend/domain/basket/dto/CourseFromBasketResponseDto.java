package com.example.courseregistratioonbackend.domain.basket.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CourseFromBasketResponseDto {

	private final Long basketId;
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
	private final Long numberOfBasket;    // 신청인원

}
