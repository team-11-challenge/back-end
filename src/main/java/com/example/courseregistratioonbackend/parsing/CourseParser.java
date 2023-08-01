package com.example.courseregistratioonbackend.parsing;

import com.example.courseregistratioonbackend.entity.Course;
import com.example.courseregistratioonbackend.entity.Major;
import com.example.courseregistratioonbackend.entity.Professor;
import com.example.courseregistratioonbackend.entity.Subject;

public class CourseParser implements Parser<Course> {

    @Override
    public Course parse(String str) {
        // 연번0,구분1,과목코드2,분반3,교과목명4,시수5,부문6,대상학과및학년7,대상인원8,대학9,학과10,전공11,교번12,담당교수13,직종14,"강의실",비고,이러닝강좌,수강용과목,폐강여부
        String[] strings = str.split("\"");
        String[] splitted = strings[0].split(",");

        Major major = Major.builder()
                .major(splitted[10])
                .majorCD(0L)
                .build();

        Subject subject = Subject.builder()
                .subject(splitted[4])
                .subjectCD(Long.valueOf(splitted[2]))
                .build();

        Professor professor = Professor.builder()
                .name(splitted[13])
                .build();

        Course course = Course.builder()
                .sort(splitted[1])
                .division(Integer.parseInt(splitted[3]))
                .credit(Integer.parseInt(splitted[5].substring(0, 1)))
                .timetable(strings[1])
                .limitation(Long.valueOf(splitted[8]))
                .yearCourse(2023)
                .semester(2)
                .major(major)
                .subject(subject)
                .professor(professor)
                .build();

        return course;
    }
}
