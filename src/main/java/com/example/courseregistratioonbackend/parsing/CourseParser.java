package com.example.courseregistratioonbackend.parsing;

import com.example.courseregistratioonbackend.entity.Course;
import com.example.courseregistratioonbackend.entity.Major;
import com.example.courseregistratioonbackend.entity.Professor;
import com.example.courseregistratioonbackend.entity.Subject;

import java.util.LinkedList;
import java.util.Stack;

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
                .timetable(pretreatment(strings[1]))
                .limitation(Long.valueOf(splitted[8]))
                .yearCourse(2023)
                .semester(2)
                .major(major)
                .subject(subject)
                .professor(professor)
                .build();

        return course;
    }

    public String pretreatment(String timetable){
        String ans = "";

        LinkedList<String> fList = new LinkedList<>();
        String[] list = timetable.split(",");
        for(int i = 0; i < list.length; i++){
            // 공백 제거
            list[i].trim();
            // 장소 제거
            if(list[i].contains("(")){
                list[i] = list[i].substring(0, list[i].indexOf("("));
            }
            // 여러글자면 나눠주기
            if(list[i].length() != 1 && !isInteger(list[i])){
                fList.add(list[i].substring(0,1));
                fList.add(list[i].substring(1));
            }else if(list[i].length() == 1 || isInteger(list[i])){
                fList.add(list[i]);
            }
        }

        ans += fList.get(0);

        for(int i = 1; i < fList.size(); i++){
            if(isInteger(fList.get(i))){
                ans += " " + fList.get(i);
            }else{
                ans += "," + fList.get(i);
            }
        }

        return ans;
    }

    public boolean isInteger(String strValue){
        try{
            Integer.parseInt(strValue);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
